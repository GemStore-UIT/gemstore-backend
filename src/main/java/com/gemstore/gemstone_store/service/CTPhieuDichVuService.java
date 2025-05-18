package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CTPhieuDichVuService {
    List<CTPhieuDichVu> getAll();
    Optional<CTPhieuDichVu> getById(CTPhieuDichVuId id);
    ResponseEntity<?> save(CTPhieuDichVu ct) throws Exception;
    void delete(CTPhieuDichVuId id);
}
