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
    @NotNull
    @NotBlank(message = "Mã phiếu dịch vụ không được để trống")
    private PhieuDichVu phieuDichVu;

    @MapsId("maLDV")
    @ManyToOne
    @JoinColumn(name = "MaLDV", nullable = false)
    @NotNull
    @NotBlank(message = "Mã loại dịch vụ không được để trống")
    private LoaiDichVu loaiDichVu;

    @Column(name = "DonGia")
    @NotBlank(message = "Đơn giá không được để trống")
    private int donGia;

    @Column(name = "SoLuong")
    @NotBlank(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int soLuong;

    @Column(name = "ThanhTien")
    @Min(value = 0)
    private int thanhTien;

    @Column(name = "TraTruoc")
    @NotBlank(message = "Tiền trả trước không được để trống")
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
