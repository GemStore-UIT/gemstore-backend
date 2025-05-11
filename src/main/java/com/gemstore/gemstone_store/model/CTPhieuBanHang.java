package com.gemstore.gemstone_store.model;

import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CT_PHIEUBANHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuBanHang {

    @EmbeddedId
    private CTPhieuBanHangId id;

    @MapsId("maSanPham")
    @ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
    private SanPham sanPham;

    @MapsId("soPhieuBH")
    @ManyToOne
    @JoinColumn(name = "SoPhieuBH", nullable = false)
    private PhieuBanHang phieuBanHang;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "ThanhTien")
    private int thanhTien;
}
