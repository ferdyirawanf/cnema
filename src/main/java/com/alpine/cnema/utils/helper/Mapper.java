package com.alpine.cnema.utils.helper;

import com.alpine.cnema.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapper {
    public void transactionMovieToMap(Transaction transaction, List<Map<String, String>> items) {
        for (TransactionMovie transactionMovie : transaction.getMovieTransaction()) {
            Map<String, String> item = new HashMap<>();
            String name = transactionMovie.getSeat().getSection().getMovie().getTitle() + "_"
                    + transactionMovie.getSeat().getSection().getCinema().getName() + "_"
                    + transactionMovie.getSeat().getSection().getStudio() + "_"
                    + transactionMovie.getSeat().getSeatNumber();

            item.put("id", transactionMovie.getId().toString());
            item.put("price", transactionMovie.getPrice().toString());
            item.put("quantity", "1");
            item.put("name", name);
            item.put("brand", "Cnema");
            item.put("category", "Movie");
            item.put("merchant_name", "Alpine");

            items.add(item);
        }
    }

    public void transactionFoodToMap(Transaction transaction, List<Map<String, String>> items) {
        for (TransactionFood transactionFood : transaction.getFoodTransaction()) {
            Map<String, String> item = new HashMap<>();

            item.put("id", transactionFood.getId().toString());
            item.put("price", transactionFood.getFnb().getPrice().toString());
            item.put("quantity", transactionFood.getQuantity().toString());
            item.put("name", transactionFood.getFnb().getName());
            item.put("brand", "Cnema");
            item.put("category", "FnB");
            item.put("merchant_name", "Alpine");

            items.add(item);
        }
    }

    public void transactionMerchToMap(TransactionMerch transaction, List<Map<String, String>> items) {
        for (TransactionMerchDetail transactionMerch : transaction.getMerchTransaction()) {
            Map<String, String> item = new HashMap<>();

            item.put("id", transactionMerch.getId().toString());
            item.put("price", transactionMerch.getMerch().getPrice().toString());
            item.put("quantity", transactionMerch.getQuantity().toString());
            item.put("name", transactionMerch.getMerch().getName());
            item.put("brand", "Cnema");
            item.put("category", "FnB");
            item.put("merchant_name", "Alpine");

            items.add(item);
        }
    }

    public Map<String, Object> userDetailToMap(User user) {
        Map<String, Object> billingAddres = new HashMap<>();
        billingAddres.put("first_name", user.getUsername());
        billingAddres.put("email", user.getEmail());
        billingAddres.put("phone", user.getPhone());
        billingAddres.put("address", user.getAddress());

        Map<String, Object> custDetail = new HashMap<>();
        custDetail.put("first_name", user.getUsername());
        custDetail.put("email", user.getEmail());
        custDetail.put("phone", user.getPhone());
        custDetail.put("billing_address", billingAddres);

        return custDetail;
    }
}
