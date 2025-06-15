package com.gemstore.gemstone_store.mapper;

import com.gemstore.gemstone_store.dto.response.CTPhieuBanHangResponse;
import com.gemstore.gemstone_store.model.CTPhieuBanHang;

public class CTPhieuBanHangMapper {
    public static CTPhieuBanHangResponse toDto(CTPhieuBanHang ct) {
        CTPhieuBanHangResponse dto = new CTPhieuBanHangResponse();
        dto.setMaSanPham(ct.getSanPham().getMaSanPham());
        dto.setTenSanPham(ct.getSanPham().getTenSanPham());
        dto.setSoLuong(ct.getSoLuong());
        dto.setThanhTien(ct.getThanhTien());
        if (ct.getPhieuBanHang() != null)
            dto.setSoPhieuBanHang(ct.getPhieuBanHang().getSoPhieuBH());
        return dto;
    }
}
