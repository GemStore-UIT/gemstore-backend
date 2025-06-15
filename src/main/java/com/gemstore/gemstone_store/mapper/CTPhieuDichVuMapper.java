package com.gemstore.gemstone_store.mapper;

import com.gemstore.gemstone_store.dto.response.CTPhieuDichVuResponse;
import com.gemstore.gemstone_store.model.CTPhieuDichVu;

public class CTPhieuDichVuMapper {
    public static CTPhieuDichVuResponse toDto(CTPhieuDichVu ct) {
        CTPhieuDichVuResponse dto = new CTPhieuDichVuResponse();
        dto.setSoPhieuDichVu(ct.getPhieuDichVu().getSoPhieuDV());
        dto.setMaLDV(ct.getLoaiDichVu().getMaLDV());
        dto.setTenLoaiDichVu(ct.getLoaiDichVu().getTenLDV());
        dto.setDonGia(ct.getDonGia());
        dto.setSoLuong(ct.getSoLuong());
        dto.setThanhTien(ct.getThanhTien());
        dto.setTraTruoc(ct.getTraTruoc());
        dto.setConLai(ct.getConLai());
        dto.setNgayGiao(ct.getNgayGiao());
        dto.setTinhTrang(ct.getTinhTrang());
        return dto;
    }
}
