package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.PhieuMuaHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhieuMuaHangRepository extends JpaRepository<PhieuMuaHang, UUID> {
}
