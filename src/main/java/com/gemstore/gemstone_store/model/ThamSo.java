package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "THAMSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThamSo {

    @Id
    @Column(name = "TenThamSo", length = 30, nullable = false)
    @NotNull
    @NotBlank(message = "Tên tham số không được để trống")
    private String tenThamSo;

    @Column(name = "GiaTri")
    @NotBlank(message = "Giá trị không được để trống")
    private int giaTri;
}
