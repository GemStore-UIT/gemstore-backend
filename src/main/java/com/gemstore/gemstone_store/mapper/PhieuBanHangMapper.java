package com.gemstore.gemstone_store.mapper;

import com.gemstore.gemstone_store.dto.response.CTPhieuBanHangResponse;
import com.gemstore.gemstone_store.dto.response.PhieuBanHangResponse;
import com.gemstore.gemstone_store.model.PhieuBanHang;

import java.util.List;
import java.util.stream.Collectors;

public class PhieuBanHangMapper {
    public static PhieuBanHangResponse toDto(PhieuBanHang entity) {
        PhieuBanHangResponse dto = new PhieuBanHangResponse();
        dto.setSoPhieuBH(entity.getSoPhieuBH());
        dto.setNgayLap(entity.getNgayLap());
        dto.setKhachHang(entity.getKhachHang());
        dto.setTongTien(entity.getTongTien());

        if (entity.getChiTiet() != null) {
            List<CTPhieuBanHangResponse> ctDtos = entity.getChiTiet().stream()
                    .map(CTPhieuBanHangMapper::toDto)
                    .collect(Collectors.toList());
            dto.setChiTiet(ctDtos);
        }
        return dto;
    }
}
