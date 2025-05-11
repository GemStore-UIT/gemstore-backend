package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "LOAIDICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiDichVu {

    @Id
    @Column(name = "MaLDV", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Mã loại dịch vụ không được để trống")
    private String maLDV;

    @Column(name = "TenLDV", length = 50)
    @NotBlank(message = "Tên loại dịch vụ không được để trống")
    private String tenLDV;

    @Column(name = "DonGia")
    @NotBlank(message = "Đơn giá không được để trống")
    @Min(value = 0, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private int donGia;

    @Column(name = "TraTruoc")
    @Min(value = 0, message = "Tiền trả trước phải lớn hơn hoặc bằng 0")
    private int traTruoc;
}
