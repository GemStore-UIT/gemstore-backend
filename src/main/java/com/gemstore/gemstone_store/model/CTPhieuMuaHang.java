package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "CT_PHIEUMUAHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuMuaHang {

    @EmbeddedId
    @JsonIgnore
    private CTPhieuMuaHangId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maSanPham")
    @JoinColumn(name = "MaSanPham", nullable = false)
    @NotNull(message = "Mã sản phẩm không được để trống")
    private SanPham sanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("soPhieuMH")
    @JoinColumn(name = "SoPhieuMH", nullable = false)
    @NotNull(message = "Số phiếu mua hàng không được để trống")
    private PhieuMuaHang phieuMuaHang;

    @Column(name = "SoLuong")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int soLuong;

    @Column(name = "ThanhTien")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0, message = "Thành tiền phải lớn hơn hoặc bằng 0")
    private int thanhTien;
}
