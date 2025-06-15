package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PHIEUBANHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuBanHang {

    @Id
    @GeneratedValue
    @Column(name = "sophieubh", length = 20, nullable = false)
    private UUID soPhieuBH;

    @Column(name = "ngaylap")
    private LocalDateTime ngayLap;

    @Column(name = "khachhang", length = 50)
    @NotBlank(message = "Khách hàng không được để trống")
    private String khachHang;

    @Column(name = "tongtien")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0, message = "Tổng tiền phải lớn hơn 0")
    private Integer tongTien = 0;

    @OneToMany(mappedBy = "phieuBanHang", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CTPhieuBanHang> chiTiet;
}
