package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.PhieuBanHang;

import java.util.List;
import java.util.Optional;

public interface PhieuBanHangService {
    List<PhieuBanHang> getAll();
    Optional<PhieuBanHang> getById(String id);
    PhieuBanHang save(PhieuBanHang pbh);
    void delete(String id);
    void updateTongTien(String soPhieuBH);
}
