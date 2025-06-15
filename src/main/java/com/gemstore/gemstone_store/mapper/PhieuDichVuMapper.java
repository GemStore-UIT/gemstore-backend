package com.gemstore.gemstone_store.mapper;

import com.gemstore.gemstone_store.dto.response.CTPhieuDichVuResponse;
import com.gemstore.gemstone_store.dto.response.PhieuDichVuResponse;
import com.gemstore.gemstone_store.model.PhieuDichVu;

import java.util.List;
import java.util.stream.Collectors;

public class PhieuDichVuMapper {
    public static PhieuDichVuResponse toDto(PhieuDichVu entity) {
        PhieuDichVuResponse dto = new PhieuDichVuResponse();
        dto.setSoPhieuDV(entity.getSoPhieuDV());
        dto.setNgayLap(entity.getNgayLap());
        dto.setKhachHang(entity.getKhachHang());
        dto.setSdt(entity.getSdt());
        dto.setTongTien(entity.getTongTien());
        dto.setTongTienTraTruoc(entity.getTongTienTraTruoc());
        dto.setTongTienConLai(entity.getTongTienConLai());
        dto.setTinhTrang(entity.getTinhTrang());

        if (entity.getChiTiet() != null) {
            List<CTPhieuDichVuResponse> ctDtos = entity.getChiTiet().stream()
                    .map(CTPhieuDichVuMapper::toDto)
                    .collect(Collectors.toList());
            dto.setChiTiet(ctDtos);
        }
        return dto;
    }
}
