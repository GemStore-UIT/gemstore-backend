package com.gemstore.gemstone_store.model;

import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "CT_PHIEUBANHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuBanHang {

    @EmbeddedId
    @NotNull
    private CTPhieuBanHangId id;

    @MapsId("maSanPham")
    @ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
    @NotNull
    @NotBlank(message = "Mã sản phẩm không được để trống")
    private SanPham sanPham;

    @MapsId("soPhieuBH")
    @ManyToOne
    @JoinColumn(name = "SoPhieuBH", nullable = false)
    @NotNull
    @NotBlank(message = "Số phiếu mua hàng không được để trống")
    private PhieuBanHang phieuBanHang;

    @Column(name = "SoLuong")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int soLuong;

    @Column(name = "ThanhTien")
    @Min(value = 0, message = "Thành tiền phải lớn hơn hoặc bằng 0")
    private int thanhTien;
}
