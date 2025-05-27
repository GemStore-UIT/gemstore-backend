package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.LoaiDichVu;

import java.util.List;
import java.util.Optional;

public interface LoaiDichVuService {
    List<LoaiDichVu> getAll();
    Optional<LoaiDichVu> getById(String id);
    LoaiDichVu save(LoaiDichVu ldv);
    void delete(String id);

    List<LoaiDichVu> getAllByName(String name);
}
