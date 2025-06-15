package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.dto.request.PhieuMuaHangRequest;
import com.gemstore.gemstone_store.dto.response.PhieuMuaHangResponse;
import com.gemstore.gemstone_store.model.PhieuMuaHang;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhieuMuaHangService {
    List<PhieuMuaHangResponse> getAll();
    Optional<PhieuMuaHangResponse> getById(UUID id);
    PhieuMuaHang save(PhieuMuaHang pmh);
    PhieuMuaHangResponse saveWithCT(PhieuMuaHangRequest pmh);
    void delete(UUID id);
    void updateTongTien(UUID soPhieuMH);
}
