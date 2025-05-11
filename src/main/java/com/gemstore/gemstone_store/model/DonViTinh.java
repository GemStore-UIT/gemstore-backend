package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DONVITINH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonViTinh {

    @Id
    @Column(name = "MaDonVi", length = 20, nullable = false)
    private String maDonVi;

    @Column(name = "TenDonVi", length = 30)
    private String tenDonVi;

}
