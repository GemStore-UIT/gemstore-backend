package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.DonViTinh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DonViTinhRepository extends JpaRepository<DonViTinh, UUID> {
    List<DonViTinh> findByTenDonViContainingIgnoreCase(String name);
}
