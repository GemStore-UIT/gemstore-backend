package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.PhieuBanHang;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhieuBanHangService {
    List<PhieuBanHang> getAll();
    Optional<PhieuBanHang> getById(UUID id);
    PhieuBanHang save(PhieuBanHang pbh);
    void delete(UUID id);
    void updateTongTien(UUID soPhieuBH);
}
