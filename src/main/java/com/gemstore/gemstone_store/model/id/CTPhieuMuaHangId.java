package com.gemstore.gemstone_store.model.id;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuMuaHangId implements Serializable {

    @NotNull
    private String maSanPham;

    @NotNull
    private String soPhieuMH;
}
