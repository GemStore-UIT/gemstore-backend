package com.gemstore.gemstone_store.model;

import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CT_PHIEUMUAHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuMuaHang {

    @EmbeddedId
    private CTPhieuMuaHangId id;

    @ManyToOne
    @MapsId("maSanPham")
    @JoinColumn(name = "MaSanPham", nullable = false)
    private SanPham sanPham;

    @ManyToOne
    @MapsId("soPhieuMH")
    @JoinColumn(name = "SoPhieuMH", nullable = false)
    private PhieuMuaHang phieuMuaHang;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "ThanhTien")
    private int thanhTien;
}
