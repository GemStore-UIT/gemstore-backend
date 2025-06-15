package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.PhieuBanHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhieuBanHangRepository extends JpaRepository<PhieuBanHang, UUID> {
}
