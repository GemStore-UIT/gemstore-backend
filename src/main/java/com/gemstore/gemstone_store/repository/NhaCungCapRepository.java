package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.NhaCungCap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, UUID> {
    List<NhaCungCap> findByTenNCCContainingIgnoreCase(String name);
}