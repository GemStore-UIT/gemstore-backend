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
@Table(name = "LOAISANPHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiSanPham {

    @Id
    @GeneratedValue
    @Column(name = "malsp", length = 20, nullable = false)
    private UUID maLSP;

    @Column(name = "tenlsp", length = 50)
    @NotBlank(message = "Tên loại sản phẩm không được để trống")
    private String tenLSP;

    @ManyToOne
    @JoinColumn(name = "donvitinh", referencedColumnName = "madonvi", nullable = false)
    @NotNull(message = "Đơn vị tính không được để trống")
    private DonViTinh donViTinh;

    @Column(name = "loinhuan")
    @Min(value = 0, message = "Lợi nhuận phải lớn hơn 0")
    private float loiNhuan;

}
