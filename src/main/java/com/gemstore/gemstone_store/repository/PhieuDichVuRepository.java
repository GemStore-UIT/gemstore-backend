package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.PhieuDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhieuDichVuRepository extends JpaRepository<PhieuDichVu, UUID> {
}
