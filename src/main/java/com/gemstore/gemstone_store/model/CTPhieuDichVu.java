package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.*;
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
    @JsonIgnore
    private CTPhieuDichVuId id;

    @MapsId("soPhieuDV")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sophieudv", referencedColumnName = "sophieudv")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "Mã phiếu dịch vụ không được để trống")
    private PhieuDichVu phieuDichVu;

    @MapsId("maLDV")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maldv", referencedColumnName = "maldv")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "Mã loại dịch vụ không được để trống")
    private LoaiDichVu loaiDichVu;

    @Column(name = "dongia")
    @Min(value = 0, message = "Đơn giá phải lớn hơn 0")
    private int donGia;

    @Column(name = "soluong")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int soLuong;

    @Column(name = "thanhtien")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0)
    private int thanhTien;

    @Column(name = "tratruoc")
    @Min(value = 0)
    private int traTruoc;

    @Column(name = "conlai")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0)
    private int conLai;

    @Column(name = "ngaygiao")
    private LocalDateTime ngayGiao;

    @Column(name = "tinhtrang", length = 10)
    @Pattern(regexp = "Đã giao|Chưa giao", message = "Tình trạng phải là 'Đã giao' hoặc 'Chưa giao'")
    private String tinhTrang;
}
