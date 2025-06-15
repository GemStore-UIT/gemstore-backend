package com.gemstore.gemstone_store.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class CTPhieuBanHangResponse {
    private UUID maSanPham;
    private String tenSanPham;
    private int soLuong;
    private int thanhTien;
    private UUID soPhieuBanHang;
}
