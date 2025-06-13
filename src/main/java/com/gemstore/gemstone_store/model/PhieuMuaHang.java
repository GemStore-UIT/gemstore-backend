package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUMUAHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuMuaHang {

    @Id
    @Column(name = "sophieumh", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Số phiếu mua hàng không được để trống")
    private String soPhieuMH;

    @Column(name = "ngaylap")
    private LocalDateTime ngayLap;

    @ManyToOne
    @NotNull(message = "Nhà cung cấp không được để trống")
    @JoinColumn(name = "mancc", nullable = false)
    private NhaCungCap nhaCungCap;

    @Column(name = "tongtien")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0, message = "Tổng tiền phải lớn hơn 0")
    private int tongTien;
}
