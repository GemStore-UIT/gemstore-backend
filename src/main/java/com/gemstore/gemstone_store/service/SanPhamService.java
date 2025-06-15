package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.SanPham;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SanPhamService {

    List<SanPham> getAll();
    Optional<SanPham> getById(UUID id);
    SanPham save(SanPham sp);
    void delete(UUID id);

    List<SanPham> getAllByName(String name);
}
