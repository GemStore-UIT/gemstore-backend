package com.gemstore.gemstone_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0)
    private int tongTien;

    @Column(name = "TongTienTraTruoc")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0)
    private int tongTienTraTruoc;

    @Column(name = "TongTienConLai")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0)
    private int tongTienConLai;

    @Column(name = "TinhTrang", length = 20)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Pattern(regexp = "Hoàn thành|Chưa hoàn thành", message = "Tình trạng phải là 'Hoàn thành' hoặc 'Chưa hoàn thành'")
    private String tinhTrang;

//    @Column
//    @OneToMany(mappedBy = "phieuDichVu", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JsonIgnore
//    private Set<CTPhieuDichVu> ctphieudichvus = new HashSet<>();
}
