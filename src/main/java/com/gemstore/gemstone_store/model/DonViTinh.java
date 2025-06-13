package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "DONVITINH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonViTinh {

    @Id
    @Column(name = "madonvi", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Mã đơn vị không được để trống")
    private String maDonVi;

    @Column(name = "tendonvi", length = 30)
    @NotBlank(message = "Tên đơn vị không được để trống")
    private String tenDonVi;

}
