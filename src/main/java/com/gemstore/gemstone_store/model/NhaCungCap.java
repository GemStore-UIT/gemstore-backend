package com.gemstore.gemstone_store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NHACUNGCAP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhaCungCap {

    @Id
    @Column(name = "MaNCC", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Mã nhà cung cấp không được để trống")
    private String maNCC;

    @Column(name = "TenNCC", length = 50)
    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    private String tenNCC;

    @Column(name = "DiaChi", length = 80)
    @NotBlank(message = "Địa chỉ nhà cung cấp không được để trống")
    private String diaChi;

    @Column(name = "SDT", length = 20)
    @NotBlank(message = "SDT nhà cung cấp không được để trống")
    private String sdt;
}
