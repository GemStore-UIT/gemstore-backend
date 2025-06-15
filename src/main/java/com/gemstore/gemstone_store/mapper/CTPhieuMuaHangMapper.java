package com.gemstore.gemstone_store.mapper;

import com.gemstore.gemstone_store.dto.response.CTPhieuMuaHangResponse;
import com.gemstore.gemstone_store.model.CTPhieuMuaHang;

public class CTPhieuMuaHangMapper {
    public static CTPhieuMuaHangResponse toDto(CTPhieuMuaHang ct) {
        CTPhieuMuaHangResponse dto = new CTPhieuMuaHangResponse();
        dto.setMaSanPham(ct.getSanPham().getMaSanPham());
        dto.setTenSanPham(ct.getSanPham().getTenSanPham());
        dto.setSoLuong(ct.getSoLuong());
        dto.setThanhTien(ct.getThanhTien());
        if (ct.getPhieuMuaHang() != null)
            dto.setSoPhieuMuaHang(ct.getPhieuMuaHang().getSoPhieuMH());
        return dto;
    }
}
