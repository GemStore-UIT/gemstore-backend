package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.SanPham;

import java.util.List;
import java.util.Optional;

public interface SanPhamService {

    List<SanPham> getAll();
    Optional<SanPham> getById(String id);
    SanPham save(SanPham sp);
    void delete(String id);

}
