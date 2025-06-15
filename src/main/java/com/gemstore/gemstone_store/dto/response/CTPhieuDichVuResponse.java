package com.gemstore.gemstone_store.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CTPhieuDichVuResponse {
    private UUID soPhieuDichVu;
    private UUID maLDV;
    private String tenLoaiDichVu;
    private int donGia;
    private int soLuong;
    private int thanhTien;
    private int traTruoc;
    private int conLai;
    private LocalDateTime ngayGiao;
    private String tinhTrang;
}
