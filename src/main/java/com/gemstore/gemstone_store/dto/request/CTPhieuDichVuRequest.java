package com.gemstore.gemstone_store.dto.request;

import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class CTPhieuDichVuRequest {
    private UUID maLDV;
    private int donGia;
    private int soLuong;
    private int traTruoc;
    private LocalDateTime ngayGiao;
    private String tinhTrang;
}

