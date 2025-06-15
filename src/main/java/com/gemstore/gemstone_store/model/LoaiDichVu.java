package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "LOAIDICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiDichVu {

    @Id
    @GeneratedValue
    @Column(name = "maldv", length = 20, nullable = false)
    private UUID maLDV;

    @Column(name = "tenldv", length = 50)
    @NotBlank(message = "Tên loại dịch vụ không được để trống")
    private String tenLDV;

    @Column(name = "dongia")
    @Min(value = 0, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private int donGia;

    @Column(name = "tratruoc")
    @Min(value = 0, message = "Tiền trả trước phải lớn hơn hoặc bằng 0")
    private int traTruoc;

}
