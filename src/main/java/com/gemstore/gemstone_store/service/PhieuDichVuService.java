package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.dto.request.PhieuDichVuRequest;
import com.gemstore.gemstone_store.dto.response.PhieuDichVuResponse;
import com.gemstore.gemstone_store.model.PhieuDichVu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhieuDichVuService {
    List<PhieuDichVuResponse> getAll();
    Optional<PhieuDichVuResponse> getById(UUID id);
    PhieuDichVu save(PhieuDichVu pdv);
    PhieuDichVuResponse saveWithCT(PhieuDichVuRequest pbh);
    void delete(UUID id);
    void updateTongTien(UUID soPhieuDV);
}
