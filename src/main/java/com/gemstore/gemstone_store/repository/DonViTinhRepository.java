package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.DonViTinh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonViTinhRepository extends JpaRepository<DonViTinh, String> {
    List<DonViTinh> findByTenDonViContainingIgnoreCase(String name);
}
