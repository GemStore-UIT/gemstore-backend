package com.gemstore.gemstone_store.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuDichVuId implements Serializable {

    //@Column(name = "SoPhieuDV")
    private String soPhieuDV;

    //@Column(name = "MaLDV")
    private String maLDV;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        CTPhieuDichVuId that = (CTPhieuDichVuId) o;
//        return soPhieuDV.equals(that.soPhieuDV) && maLDV.equals(that.maLDV);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(soPhieuDV, maLDV);
//    }
}
