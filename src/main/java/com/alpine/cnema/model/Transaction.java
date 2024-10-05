package com.alpine.cnema.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "total_price")
    private Double totalPrice;

    private String paymentId;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionMovie> movieTransaction = new ArrayList<>();

    public void addTransactionMovie(TransactionMovie movie) {
        movieTransaction.add(movie);
        movie.setTransaction(this);
    }

    public void removeTransactionMovie(TransactionMovie movie) {
        movieTransaction.remove(movie);
        movie.setTransaction(null);
    }

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionFood> foodTransaction = new ArrayList<>();

    public void addTransactionFood(TransactionFood food) {
        foodTransaction.add(food);
        food.setTransaction(this);
    }

    public void removeTransactionFood(TransactionFood food) {
        foodTransaction.remove(food);
        food.setTransaction(null);
    }
}
