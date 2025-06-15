package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.dto.response.CTPhieuDichVuResponse;
import com.gemstore.gemstone_store.dto.response.PhieuDichVuResponse;
import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CTPhieuDichVuService {
    List<CTPhieuDichVuResponse> getAll();
    Optional<CTPhieuDichVuResponse> getById(CTPhieuDichVuId id);
    List<CTPhieuDichVuResponse> getAllByPhieuDV(UUID soPhieuDV);
    CTPhieuDichVu save(CTPhieuDichVu ct);
    void delete(CTPhieuDichVuId id);
}
