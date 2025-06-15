package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CTPhieuDichVuService {
    List<CTPhieuDichVu> getAll();
    Optional<CTPhieuDichVu> getById(CTPhieuDichVuId id);
    List<CTPhieuDichVu> getAllByPhieuDV(UUID soPhieuDV);
    CTPhieuDichVu save(CTPhieuDichVu ct);
    void delete(CTPhieuDichVuId id);
}
