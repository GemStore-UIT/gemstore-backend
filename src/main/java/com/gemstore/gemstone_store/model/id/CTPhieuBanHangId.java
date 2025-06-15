package com.gemstore.gemstone_store.model.id;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuBanHangId implements Serializable {
    private UUID maSanPham;
    private UUID soPhieuBH;
}
