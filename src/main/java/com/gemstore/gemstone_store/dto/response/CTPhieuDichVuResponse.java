package com.gemstore.gemstone_store.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CTPhieuDichVuResponse {
    private UUID soPhieuDV;
    private UUID maLDV;
    private String tenLDV;
    private int donGia;
    private int soLuong;
    private int traTruoc;
    private int thanhTien;
    private int conLai;
    private LocalDateTime ngayGiao;
    private String tinhTrang;
}
