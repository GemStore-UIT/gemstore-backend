package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.dto.request.PhieuBanHangRequest;
import com.gemstore.gemstone_store.dto.response.PhieuBanHangResponse;
import com.gemstore.gemstone_store.model.PhieuBanHang;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhieuBanHangService {
    List<PhieuBanHangResponse> getAll();
    Optional<PhieuBanHangResponse> getById(UUID id);
    PhieuBanHang save(PhieuBanHang pbh);
    PhieuBanHangResponse saveWithCT(PhieuBanHangRequest pbh);
    void delete(UUID id);
    void updateTongTien(UUID soPhieuBH);
}
