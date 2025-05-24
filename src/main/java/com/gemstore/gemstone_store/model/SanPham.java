package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
    @JsonIgnoreProperties({"tenLSP", "donViTinh", "loiNhuan"})
    @NotNull(message = "Loại sản phẩm không được để trống")
    private LoaiSanPham loaiSanPham;

    @Column(name = "DonGia")
    @Min(value = 0, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private Integer donGia;

    @Column(name = "TonKho")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
    private Integer tonKho;
}
