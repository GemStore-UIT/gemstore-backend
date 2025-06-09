package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SanPhamRepository extends JpaRepository<SanPham, String> {
    List<SanPham> findByTenSanPhamContainingIgnoreCase(String name);
}
