package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.TransactionDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.model.User;
import com.alpine.cnema.service.TransactionService;
import com.alpine.cnema.utils.constant.Messages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.midtrans.httpclient.error.MidtransError;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> create(
            @RequestBody TransactionDTO.CreateTransaction request,
            @AuthenticationPrincipal User user
    ) throws MidtransError, JsonProcessingException {
        JSONObject result = transactionService.create(request, user);
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(result);
        return new ResponseEntity<>(prettyJson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable) {
        return RenderJson.pageFormat(transactionService.index(pageable), HttpStatus.ACCEPTED);
    }

    @GetMapping("/histories")
    public ResponseEntity<?> getHistories(@AuthenticationPrincipal User user, Pageable pageable) {
        return RenderJson.pageFormat(transactionService.findByUser(user, pageable), HttpStatus.OK);
    }
}
