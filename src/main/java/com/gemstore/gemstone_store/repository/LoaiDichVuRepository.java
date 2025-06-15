package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.LoaiDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoaiDichVuRepository extends JpaRepository<LoaiDichVu, UUID> {
    List<LoaiDichVu> findByTenLDVContainingIgnoreCase(String name);
}
