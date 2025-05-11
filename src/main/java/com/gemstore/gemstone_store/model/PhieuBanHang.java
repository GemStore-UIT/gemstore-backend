package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotBlank(message = "Số phiếu bán hàng không được để trống")
    private String soPhieuBH;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "KhachHang", length = 50)
    @NotBlank(message = "Số phiếu bán hàng không được để trống")
    private String khachHang;
}
