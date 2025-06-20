package com.gemstore.gemstone_store.model.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuMuaHangId implements Serializable {
    private UUID maSanPham;
    private UUID soPhieuMH;
}
