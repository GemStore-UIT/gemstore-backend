package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.PhieuDichVu;

import java.util.List;
import java.util.Optional;

public interface PhieuDichVuService {
    List<PhieuDichVu> getAll();
    Optional<PhieuDichVu> getById(String id);
    PhieuDichVu save(PhieuDichVu pdv);
    void delete(String id);
    void updateTongTien(String soPhieuDV);
}
