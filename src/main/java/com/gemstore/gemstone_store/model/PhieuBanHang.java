package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUBANHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuBanHang {

    @Id
    @Column(name = "SoPhieuBH", length = 20, nullable = false)
    private String soPhieuBH;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "KhachHang", length = 50)
    private String khachHang;
}
