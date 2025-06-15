package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.LoaiDichVu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoaiDichVuService {
    List<LoaiDichVu> getAll();
    Optional<LoaiDichVu> getById(UUID id);
    LoaiDichVu save(LoaiDichVu ldv);
    void delete(UUID id);

    List<LoaiDichVu> getAllByName(String name);
}
