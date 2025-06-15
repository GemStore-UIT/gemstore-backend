package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "DONVITINH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonViTinh {

    @Id
    @GeneratedValue
    @Column(name = "madonvi", length = 20, nullable = false)
    private UUID maDonVi;

    @Column(name = "tendonvi", length = 30)
    @NotBlank(message = "Tên đơn vị không được để trống")
    private String tenDonVi;

}
