package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.*;
import com.alpine.cnema.model.*;
import com.alpine.cnema.repository.TransactionRepository;
import com.alpine.cnema.service.FnBService;
import com.alpine.cnema.service.PaymentService;
import com.alpine.cnema.service.SeatService;
import com.alpine.cnema.service.TransactionService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.helper.Mapper;
import com.midtrans.httpclient.CoreApi;
import com.midtrans.httpclient.error.MidtransError;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;
    private final SeatService seatService;
    private final FnBService foodService;

    @Override
    public Page<TransactionDTO.TransactionBasicFormat> index(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(TransactionDTO.TransactionBasicFormat::from);
    }

    @Override
    public JSONObject create(TransactionDTO.CreateTransaction transaction, User user) throws MidtransError {
        Transaction trans = new Transaction();
        trans.setUser(user);
        Double totalAmount = 0.0;

        for (TransactionDTO.CreateTransactionMovie movie : transaction.getTransactionMovie()) {
            TransactionMovie transactionMovie = createMovie(movie);
            trans.addTransactionMovie(transactionMovie);
            totalAmount += (transactionMovie.getSeat().getSection().getMovie().getPrice());
        }

        for (TransactionDTO.CreateTransactionFood food : transaction.getTransactionFood()) {
            TransactionFood transactionFood = createFood(food);
            trans.addTransactionFood(transactionFood);
            totalAmount += (transactionFood.getFnb().getPrice() * transactionFood.getQuantity());
        }

        trans.setTotalPrice(totalAmount);
        Transaction savedTransaction = transactionRepository.save(trans);

        Map<String, Object> body = mapper(savedTransaction, user);

        JSONObject jsonObject = paymentService.gopay(body, savedTransaction);
        Transaction finalTransaction = setPaymentId(savedTransaction.getId(), jsonObject.get("transaction_id").toString());

        return jsonObject;
    }

    public Transaction setPaymentId(Integer id, String paymentId) {
        Transaction transaction = show(id);

        if (transaction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.TRANSACTION_EMPTY);
        }

        transaction.setPaymentId(paymentId);

        return transactionRepository.save(transaction);
    }

    @Override
    public Page<TransactionDTO.TransactionBasicFormat> findByUser(User user, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByUser(user, pageable);
        return transactions.map(TransactionDTO.TransactionBasicFormat::from);
    }

    @Override
    public Transaction show(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    private TransactionMovie createMovie(TransactionDTO.CreateTransactionMovie detail) {
        if (detail.getSeatId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SEAT_EMPTY);
        }

        Seat seat = seatService.show(detail.getSeatId());
        if (seat == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SEAT_NOT_FOUND);
        }
        if (seat.getIsBooked()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SEAT_BOOKED);
        }

        TransactionMovie transactionMovie = TransactionMovie.builder()
                .seat(seat)
                .price(seatService.show(detail.getSeatId()).getSection().getMovie().getPrice())
                .build(); 

        seatService.update(detail.getSeatId(), new SeatDTO(seat.getSeatNumber(),
                seat.getSection().getId(),
                Boolean.TRUE,
                seat.getSection().getMovie().getTitle(),
                seat.getSection().getCinema().getName()
                ));

        return transactionMovie;
    }

    private TransactionFood createFood(TransactionDTO.CreateTransactionFood detail) {
        if (detail.getFnbId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.FNB_EMPTY);
        }

        if (detail.getQuantity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.QUANTITY_EMPTY);
        }

        FnB food = foodService.show(detail.getFnbId());
        if (food == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.FNB_NOT_FOUND);
        }

        return TransactionFood.builder()
                .fnb(food)
                .quantity(detail.getQuantity())
                .build();
    }

    @Override
    public Map<String, Object> mapper(Transaction transaction, User user) {
        MidtransDTO dto = new MidtransDTO();
        dto.setPaymentType("gopay"); // TODO get type payment dynamic

        Map<String, String> transDetail = new HashMap<>();
        transDetail.put("order_id", "MOVIE_" + new Timestamp(System.currentTimeMillis()).getTime());
        transDetail.put("gross_amount", transaction.getTotalPrice().toString());

        List<Map<String, String>> items = new ArrayList<>();
        Mapper mapperMidtrans = new Mapper();
        mapperMidtrans.transactionMovieToMap(transaction, items);
        mapperMidtrans.transactionFoodToMap(transaction, items);

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