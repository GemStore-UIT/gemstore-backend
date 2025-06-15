package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.PhieuDichVu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhieuDichVuService {
    List<PhieuDichVu> getAll();
    Optional<PhieuDichVu> getById(UUID id);
    PhieuDichVu save(PhieuDichVu pdv);
    void delete(UUID id);
    void updateTongTien(UUID soPhieuDV);
}
