package com.alpine.cnema.model;

import com.alpine.cnema.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "merchandises")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Merchandise extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private String company;
}
