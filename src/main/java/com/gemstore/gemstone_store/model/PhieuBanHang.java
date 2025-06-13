package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    @Column(name = "sophieubh", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Số phiếu bán hàng không được để trống")
    private String soPhieuBH;

    @Column(name = "ngaylap")
    private LocalDateTime ngayLap;

    @Column(name = "khachhang", length = 50)
    @NotBlank(message = "Khách hàng không được để trống")
    private String khachHang;

    @Column(name = "tongtien")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0, message = "Tổng tiền phải lớn hơn 0")
    private int tongTien;
}
