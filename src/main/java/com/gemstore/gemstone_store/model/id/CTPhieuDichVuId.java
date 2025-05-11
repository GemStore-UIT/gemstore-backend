package com.gemstore.gemstone_store.model.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuDichVuId implements Serializable {
    private String soPhieuDV;
    private String maLDV;
}
