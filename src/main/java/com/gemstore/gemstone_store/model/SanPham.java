package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SANPHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {

    @Id
    @Column(name = "MaSanPham", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSanPham;

    @Column(name = "TenSanPham", length = 50)
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String tenSanPham;

    @ManyToOne
    @JoinColumn(name = "LoaiSanPham", referencedColumnName = "MaLSP", nullable = false)
    @NotNull
    @NotBlank(message = "Loại sản phẩm không được để trống")
    private LoaiSanPham loaiSanPham;

    @Column(name = "DonGia")
    @NotBlank(message = "Đơn giá sản phẩm không được để trống")
    @Min(value = 0, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private Integer donGia;

    @Column(name = "TonKho")
    @NotBlank(message = "Số lượng tồn kho của sản phẩm không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
    private Integer tonKho;
}
