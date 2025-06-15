package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.PhieuMuaHang;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhieuMuaHangService {
    List<PhieuMuaHang> getAll();
    Optional<PhieuMuaHang> getById(UUID id);
    PhieuMuaHang save(PhieuMuaHang pmh);
    void delete(UUID id);
    void updateTongTien(UUID soPhieuMH);
}
