package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.LoaiSanPham;

import java.util.List;
import java.util.Optional;

public interface LoaiSanPhamService {

    List<LoaiSanPham> getAll();
    Optional<LoaiSanPham> getById(String id);
    LoaiSanPham save(LoaiSanPham loaiSanPham);
    void delete(String id);

}
