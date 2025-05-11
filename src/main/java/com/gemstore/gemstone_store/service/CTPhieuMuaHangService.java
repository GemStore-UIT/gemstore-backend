package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;

import java.util.List;
import java.util.Optional;

public interface CTPhieuMuaHangService {
    List<CTPhieuMuaHang> getAll();
    Optional<CTPhieuMuaHang> getById(CTPhieuMuaHangId id);
    CTPhieuMuaHang save(CTPhieuMuaHang ct);
    void delete(CTPhieuMuaHangId id);
}
