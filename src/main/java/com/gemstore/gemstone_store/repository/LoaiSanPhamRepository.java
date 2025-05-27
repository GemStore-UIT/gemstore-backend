package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.LoaiSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, String> {
    List<LoaiSanPham> findByTenLSPContainingIgnoreCase(String name);
}
