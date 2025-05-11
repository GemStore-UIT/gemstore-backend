package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LOAIDICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiDichVu {

    @Id
    @Column(name = "MaLDV", length = 20, nullable = false)
    private String maLDV;

    @Column(name = "TenLDV", length = 50)
    private String tenLDV;

    @Column(name = "DonGia")
    private int donGia;

    @Column(name = "TraTruoc")
    private int traTruoc;
}
