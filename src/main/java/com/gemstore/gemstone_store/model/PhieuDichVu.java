package com.gemstore.gemstone_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUDICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDichVu {

    @Id
    @Column(name = "SoPhieuDV", length = 20, nullable = false)
    @NotNull
    @NotBlank(message = "Số phiếu dịch vụ không được để trống")
    private String soPhieuDV;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "KhachHang", length = 50)
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String khachHang;

    @Column(name = "SDT", length = 20)
    @NotBlank(message = "SDT không được để trống")
    private String sdt;

    @Column(name = "TongTien")
    @Min(value = 0)
    private int tongTien;

    @Column(name = "TongTienTraTruoc")
    @Min(value = 0)
    private int tongTienTraTruoc;

    @Column(name = "TongTienConLai")
    @Min(value = 0)
    private int tongTienConLai;

    @Column(name = "TinhTrang", length = 20)
    @Pattern(regexp = "Hoàn thành|Chưa hoàn thành", message = "Tình trạng phải là 'Hoàn thành' hoặc 'Chưa hoàn thành'")
    private String tinhTrang;
}
