package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {
    List<SanPham> findByTenSanPhamContainingIgnoreCase(String name);
}
