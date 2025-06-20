package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CT_PHIEUBANHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuBanHang {

    @EmbeddedId
    @JsonIgnore
    private CTPhieuBanHangId id;

    @MapsId("maSanPham")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "masanpham", referencedColumnName = "masanpham")
    @NotNull(message = "Mã sản phẩm không được để trống")
    private SanPham sanPham;

    @MapsId("soPhieuBH")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    @JoinColumn(name = "sophieubh", referencedColumnName = "sophieubh")
    @NotNull(message = "Số phiếu bán hàng không được để trống")
    private PhieuBanHang phieuBanHang;

    @Column(name = "soluong")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int soLuong;

    @Column(name = "thanhtien")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0, message = "Thành tiền phải lớn hơn hoặc bằng 0")
    private int thanhTien;
}
