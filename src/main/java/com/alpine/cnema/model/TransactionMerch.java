package com.alpine.cnema.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction_merch")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionMerch extends Auditable{
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
    private List<TransactionMerchDetail> merchTransaction = new ArrayList<>();

    public void addTransactionMerch(TransactionMerchDetail merch) {
        merchTransaction.add(merch);
        merch.setTransaction(this);
    }

    public void removeTransactionMerch(TransactionMerchDetail merch) {
        merchTransaction.remove(merch);
        merch.setTransaction(null);
    }
}
