package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LOAISANPHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiSanPham {

    @Id
    @Column(name = "MaLSP", length = 20, nullable = false)
    private String maLSP;

    @Column(name = "TenLSP", length = 50)
    private String tenLSP;

    @ManyToOne
    @JoinColumn(name = "DonViTinh", referencedColumnName = "MaDonVi", nullable = false)
    private DonViTinh donViTinh;

    @Column(name = "LoiNhuan")
    private float loiNhuan;

}
