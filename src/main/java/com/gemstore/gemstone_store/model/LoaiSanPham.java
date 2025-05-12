package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "LOAISANPHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiSanPham {

    @Id
    @Column(name = "MaLSP", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Mã loại sản phẩm không được để trống")
    private String maLSP;

    @Column(name = "TenLSP", length = 50)
    @NotBlank(message = "Tên loại sản phẩm không được để trống")
    private String tenLSP;

    @ManyToOne
    @JoinColumn(name = "DonViTinh", referencedColumnName = "MaDonVi", nullable = false)
    @NotNull(message = "Đơn vị tính không được để trống")
    private DonViTinh donViTinh;

    @Column(name = "LoiNhuan")
    @Min(value = 0, message = "Lợi nhuận phải lớn hơn 0")
    private float loiNhuan;

}
