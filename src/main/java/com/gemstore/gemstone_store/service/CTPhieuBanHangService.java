package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CTPhieuBanHangService {
    List<CTPhieuBanHang> getAll();
    Optional<CTPhieuBanHang> getById(CTPhieuBanHangId id);
    List<CTPhieuBanHang> getAllByPhieuBH(UUID soPhieuBH);
    CTPhieuBanHang save(CTPhieuBanHang ct);
    void delete(CTPhieuBanHangId id);
}
