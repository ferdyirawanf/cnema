package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.MidtransDTO;
import com.alpine.cnema.dto.request.TransactionMerchDTO;
import com.alpine.cnema.model.*;
import com.alpine.cnema.repository.TransactionMerchRepository;
import com.alpine.cnema.service.MerchService;
import com.alpine.cnema.service.PaymentMerchService;
import com.alpine.cnema.service.TransactionMerchService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.helper.Mapper;
import com.midtrans.httpclient.error.MidtransError;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionMerchServiceImpl implements TransactionMerchService {
    private final TransactionMerchRepository transactionRepository;
    private final PaymentMerchService paymentService;
    private final MerchService merchService;

    @Override
    public Page<TransactionMerchDTO.TransactionBasicFormat> index(Pageable pageable) {
        Page<TransactionMerch> transactions = transactionRepository.findAll(pageable);
        return transactions.map(TransactionMerchDTO.TransactionBasicFormat::from);
    }

    @Override
    public JSONObject create(TransactionMerchDTO.CreateTransaction transaction, User user) throws MidtransError {
        if (transaction.getTransactionMerch().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.TRANSACTION_EMPTY);
        }

        TransactionMerch trans = new TransactionMerch();
        trans.setUser(user);
        Double totalAmount = 0.0;
        System.out.println(transaction);

        for (TransactionMerchDTO.CreateTransactionMerchDetail merch : transaction.getTransactionMerch()) {
            System.out.println(merch);
            TransactionMerchDetail transactionMerchDetail = createMerch(merch);
            trans.addTransactionMerch(transactionMerchDetail);
            totalAmount += (transactionMerchDetail.getMerch().getPrice() * transactionMerchDetail.getQuantity());
        }

        trans.setTotalPrice(totalAmount);
        TransactionMerch savedTransaction = transactionRepository.save(trans);

        Map<String, Object> body = mapperMidtransRequest(savedTransaction, user);

        JSONObject jsonObject = paymentService.gopay(body, savedTransaction);
        TransactionMerch finalTransaction = setPaymentId(savedTransaction.getId(), jsonObject.get("transaction_id").toString());

        return jsonObject;
    }

    public TransactionMerch setPaymentId(Integer transactionId, String paymentId) {
        TransactionMerch transactionMerch = show(transactionId);

        if (transactionMerch == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.TRANSACTION_EMPTY);
        }

        transactionMerch.setPaymentId(paymentId);
        return transactionRepository.save(transactionMerch);
    }

    @Override
    public Page<TransactionMerchDTO.TransactionBasicFormat> findByUser(User user, Pageable pageable) {
        Page<TransactionMerch> transactions = transactionRepository.findByUser(user, pageable);
        return transactions.map(TransactionMerchDTO.TransactionBasicFormat::from);
    }

    @Override
    public TransactionMerch show(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    private TransactionMerchDetail createMerch(TransactionMerchDTO.CreateTransactionMerchDetail detail) {
        if (detail.getMerchId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.MERCHANDISE_EMPTY);
        }

        if (detail.getQuantity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.QUANTITY_EMPTY);
        }

        Merchandise merch = merchService.show(detail.getMerchId());
        if (merch == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.MERCHANDISE_NOT_FOUND);
        }

        return TransactionMerchDetail.builder()
                .merch(merch)
                .quantity(detail.getQuantity())
                .build();
    }

    public Map<String, Object> mapperMidtransRequest(TransactionMerch transaction, User user) {
        MidtransDTO dto = new MidtransDTO();
        dto.setPaymentType("gopay"); // TODO get type payment dynamic

        Map<String, String> transDetail = new HashMap<>();
        transDetail.put("order_id", "MERCH_" + new Timestamp(System.currentTimeMillis()).getTime());
        transDetail.put("gross_amount", transaction.getTotalPrice().toString());

        List<Map<String, String>> items = new ArrayList<>();
        Mapper mapperMidtrans = new Mapper();
        mapperMidtrans.transactionMerchToMap(transaction, items);

        Map<String, Object> body = new HashMap<>();
        body.put("transaction_details", transDetail);
        body.put("item_details", items);
        body.put("customer_details", mapperMidtrans.userDetailToMap(user));

        if (dto.getCreditCard() != null) { body.put("credit_card", dto.getCreditCard()); }
        if (!dto.getPaymentType().isEmpty()) { body.put("payment_type", dto.getPaymentType()); }
        if (dto.getListedPayment() != null) { body.put("enabled_payments", dto.getListedPayment()); }

        return body;
    }
}
