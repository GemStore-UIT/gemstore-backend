package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.PhieuMuaHang;

import java.util.List;
import java.util.Optional;

public interface PhieuMuaHangService {
    List<PhieuMuaHang> getAll();
    Optional<PhieuMuaHang> getById(String id);
    PhieuMuaHang save(PhieuMuaHang pmh);
    void delete(String id);
    void updateTongTien(String soPhieuMH);
}
