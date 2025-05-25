package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTPhieuMuaHangRepository extends JpaRepository<CTPhieuMuaHang, CTPhieuMuaHangId> {
    List<CTPhieuMuaHang> findByPhieuMuaHang_SoPhieuMH(String soPhieuMuaHang);
}
