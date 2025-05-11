package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUMUAHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuMuaHang {

    @Id
    @Column(name = "SoPhieuMH", length = 20, nullable = false)
    private String soPhieuMH;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @ManyToOne
    @JoinColumn(name = "MaNCC", nullable = false)
    private NhaCungCap nhaCungCap;
}
