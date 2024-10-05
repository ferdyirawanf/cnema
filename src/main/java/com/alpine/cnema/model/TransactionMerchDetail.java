package com.alpine.cnema.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transaction_merch_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionMerchDetail extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "transaction_merch_id", referencedColumnName = "id")
    private TransactionMerch transaction;

    @ManyToOne
    @JoinColumn(name = "merchandise_id", referencedColumnName = "id")
    private Merchandise merch;
}
