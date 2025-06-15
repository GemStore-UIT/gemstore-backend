package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.LoaiSanPham;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoaiSanPhamService {

    List<LoaiSanPham> getAll();
    Optional<LoaiSanPham> getById(UUID id);
    LoaiSanPham save(LoaiSanPham loaiSanPham);
    void delete(UUID id);

    List<LoaiSanPham> getAllByName(String name);

}
