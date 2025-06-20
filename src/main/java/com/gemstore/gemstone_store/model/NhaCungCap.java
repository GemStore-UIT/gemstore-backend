package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "NHACUNGCAP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhaCungCap {

    @Id
    @GeneratedValue
    @Column(name = "mancc", length = 20, nullable = false)
    private UUID maNCC;

    @Column(name = "tenncc", length = 50)
    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    private String tenNCC;

    @Column(name = "diachi", length = 80)
    @NotBlank(message = "Địa chỉ nhà cung cấp không được để trống")
    private String diaChi;

    @Column(name = "sdt", length = 20)
    @NotBlank(message = "SDT nhà cung cấp không được để trống")
    private String sdt;
}
