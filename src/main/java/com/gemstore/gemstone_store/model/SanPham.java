package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SANPHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {

    @Id
    @Column(name = "MaSanPham", length = 20, nullable = false)
    private String maSanPham;

    @Column(name = "TenSanPham", length = 50)
    private String tenSanPham;

    @ManyToOne
    @JoinColumn(name = "LoaiSanPham", referencedColumnName = "MaLSP", nullable = false)
    private LoaiSanPham loaiSanPham;

    @Column(name = "DonGia")
    private Integer donGia;

    @Column(name = "TonKho")
    private Integer tonKho;
}
