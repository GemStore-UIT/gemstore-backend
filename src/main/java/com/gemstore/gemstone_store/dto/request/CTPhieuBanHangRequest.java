package com.gemstore.gemstone_store.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CTPhieuBanHangRequest {
    private UUID maSanPham;
    private int soLuong;
}