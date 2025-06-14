package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CTPhieuBanHangRepository extends JpaRepository<CTPhieuBanHang, CTPhieuBanHangId> {
    List<CTPhieuBanHang> findByPhieuBanHang_SoPhieuBH(UUID soPhieuBH);
}
