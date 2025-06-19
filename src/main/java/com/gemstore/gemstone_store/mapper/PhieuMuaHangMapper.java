package com.gemstore.gemstone_store.mapper;

import com.gemstore.gemstone_store.dto.response.CTPhieuMuaHangResponse;
import com.gemstore.gemstone_store.dto.response.PhieuMuaHangResponse;
import com.gemstore.gemstone_store.model.PhieuMuaHang;

import java.util.List;
import java.util.stream.Collectors;

public class PhieuMuaHangMapper {
    public static PhieuMuaHangResponse toDto(PhieuMuaHang entity) {
        PhieuMuaHangResponse dto = new PhieuMuaHangResponse();
        dto.setSoPhieuMH(entity.getSoPhieuMH());
        dto.setNgayLap(entity.getNgayLap());
        dto.setNhaCungCap(entity.getNhaCungCap() != null ? entity.getNhaCungCap() : null);
        dto.setTongTien(entity.getTongTien());
        if (entity.getChiTiet() != null) {
            List<CTPhieuMuaHangResponse> ctDtos = entity.getChiTiet().stream()
                    .map(CTPhieuMuaHangMapper::toDto)
                    .collect(Collectors.toList());
            dto.setChiTiet(ctDtos);
        }
        return dto;
    }
}
