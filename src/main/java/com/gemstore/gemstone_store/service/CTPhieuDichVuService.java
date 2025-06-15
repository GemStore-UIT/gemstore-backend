package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.dto.request.CTPhieuDichVuRequest;
import com.gemstore.gemstone_store.dto.response.CTPhieuDichVuResponse;
import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CTPhieuDichVuService {
    List<CTPhieuDichVuResponse> getAll();
    Optional<CTPhieuDichVuResponse> getById(CTPhieuDichVuId id);
    List<CTPhieuDichVuResponse> getAllByPhieuDV(UUID soPhieuDV);
    CTPhieuDichVuResponse save(CTPhieuDichVuRequest ct);
    void delete(CTPhieuDichVuId id);
}
