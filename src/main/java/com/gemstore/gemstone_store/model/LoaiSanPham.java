package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
