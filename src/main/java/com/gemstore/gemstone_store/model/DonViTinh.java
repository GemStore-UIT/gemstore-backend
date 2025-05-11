package com.gemstore.gemstone_store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "DONVITINH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonViTinh {

    @Id
    @Column(name = "MaDonVi", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Mã đơn vị không được để trống")
    private String maDonVi;

    @Column(name = "TenDonVi", length = 30)
    @NotBlank(message = "Tên đơn vị không được để trống")
    private String tenDonVi;

}
