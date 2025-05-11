package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUDICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDichVu {

    @Id
    @Column(name = "SoPhieuDV", length = 20, nullable = false)
    private String soPhieuDV;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "KhachHang", length = 50)
    private String khachHang;

    @Column(name = "SDT", length = 20)
    private String sdt;

    @Column(name = "TongTien")
    private int tongTien;

    @Column(name = "TongTienTraTruoc")
    private int tongTienTraTruoc;

    @Column(name = "TongTienConLai")
    private int tongTienConLai;
}
