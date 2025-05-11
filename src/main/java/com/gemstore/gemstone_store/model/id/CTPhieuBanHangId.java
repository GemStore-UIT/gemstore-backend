package com.gemstore.gemstone_store.model.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuBanHangId implements Serializable {
    private String maSanPham;
    private String soPhieuBH;
}
