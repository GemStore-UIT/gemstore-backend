package com.gemstore.gemstone_store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String maNCC;

    @Column(name = "TenNCC", length = 50)
    private String tenNCC;

    @Column(name = "DiaChi", length = 80)
    private String diaChi;

    @Column(name = "SDT", length = 20)
    private String sdt;
}
