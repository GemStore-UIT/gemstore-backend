package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "THAMSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThamSo {

    @Id
    @Column(name = "TenThamSo", length = 30, nullable = false)
    private String tenThamSo;

    @Column(name = "GiaTri")
    private int giaTri;
}
