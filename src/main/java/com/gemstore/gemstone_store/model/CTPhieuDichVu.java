package com.gemstore.gemstone_store.model;

import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CT_PHIEUDICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTPhieuDichVu {

    @EmbeddedId
    @NotNull
    private CTPhieuDichVuId id;

    @MapsId("soPhieuDV")
    @ManyToOne
    @JoinColumn(name = "SoPhieuDV", nullable = false)
    @NotNull(message = "Mã phiếu dịch vụ không được để trống")
    private PhieuDichVu phieuDichVu;

    @MapsId("maLDV")
    @ManyToOne
    @JoinColumn(name = "MaLDV", nullable = false)
    @NotNull(message = "Mã loại dịch vụ không được để trống")
    private LoaiDichVu loaiDichVu;

    @Column(name = "DonGia")
    @Min(value = 1, message = "Đơn giá phải lớn hơn 0")
    private int donGia;

    @Column(name = "SoLuong")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int soLuong;

    @Column(name = "ThanhTien")
    @Min(value = 0)
    private int thanhTien;

    @Column(name = "TraTruoc")
    @Min(value = 0)
    private int traTruoc;

    @Column(name = "ConLai")
    @Min(value = 0)
    private int conLai;

    @Column(name = "NgayGiao")
    private LocalDateTime ngayGiao;

    @Column(name = "TinhTrang", length = 10)
    @Pattern(regexp = "Đã giao|Chưa giao", message = "Tình trạng phải là 'Đã giao' hoặc 'Chưa giao'")
    private String tinhTrang;
}
