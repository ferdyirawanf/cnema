package com.alpine.cnema.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinemas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cinema extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String location;
}
