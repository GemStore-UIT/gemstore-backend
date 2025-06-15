package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "SANPHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {

    @Id
    @GeneratedValue
    @Column(name = "masanpham", length = 20, nullable = false)
    private UUID maSanPham;

    @Column(name = "tensanpham", length = 50)
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String tenSanPham;

    @ManyToOne
    @JoinColumn(name = "loaisanpham", referencedColumnName = "malsp", nullable = false)
    @NotNull(message = "Loại sản phẩm không được để trống")
    private LoaiSanPham loaiSanPham;

    @Column(name = "dongia")
    @Min(value = 0, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private Integer donGia;

    @Column(name = "tonkho")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
    private Integer tonKho;
}
