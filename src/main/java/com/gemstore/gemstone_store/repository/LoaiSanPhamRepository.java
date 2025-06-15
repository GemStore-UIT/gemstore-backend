package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.LoaiSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, UUID> {
    List<LoaiSanPham> findByTenLSPContainingIgnoreCase(String name);
}
