package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "masanpham", referencedColumnName = "masanpham")
    @NotNull(message = "Mã sản phẩm không được để trống")
    private SanPham sanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("soPhieuMH")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    @ToString.Exclude
    @JoinColumn(name = "sophieumh", referencedColumnName = "sophieumh")
    @NotNull(message = "Số phiếu mua hàng không được để trống")
    private PhieuMuaHang phieuMuaHang;

    @Column(name = "soluong")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int soLuong;

    @Column(name = "thanhtien")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0, message = "Thành tiền phải lớn hơn hoặc bằng 0")
    private int thanhTien;
}
