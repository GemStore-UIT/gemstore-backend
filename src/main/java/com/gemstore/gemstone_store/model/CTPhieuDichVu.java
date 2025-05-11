package com.gemstore.gemstone_store.model;

import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CT_PHIEUDICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuDichVu {

    @EmbeddedId
    private CTPhieuDichVuId id;

    @MapsId("soPhieuDV")
    @ManyToOne
    @JoinColumn(name = "SoPhieuDV", nullable = false)
    private PhieuDichVu phieuDichVu;

    @MapsId("maLDV")
    @ManyToOne
    @JoinColumn(name = "MaLDV", nullable = false)
    private LoaiDichVu loaiDichVu;

    @Column(name = "DonGia")
    private int donGia;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "ThanhTien")
    private int thanhTien;

    @Column(name = "TraTruoc")
    private int traTruoc;

    @Column(name = "ConLai")
    private int conLai;

    @Column(name = "NgayGiao")
    private LocalDateTime ngayGiao;

    @Column(name = "TinhTrang", length = 10)
    private String tinhTrang;
}
