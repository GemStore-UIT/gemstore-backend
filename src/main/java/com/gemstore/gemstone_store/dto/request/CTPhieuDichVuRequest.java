package com.gemstore.gemstone_store.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CTPhieuDichVuRequest {
    private UUID maLDV;
    private int soLuong;
    private int traTruoc;
    private LocalDateTime ngayGiao;
    private String tinhTrang;
}

