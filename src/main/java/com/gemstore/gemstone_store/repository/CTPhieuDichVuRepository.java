package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTPhieuDichVuRepository extends JpaRepository<CTPhieuDichVu, CTPhieuDichVuId> {
    List<CTPhieuDichVu> findByPhieuDichVu_SoPhieuDV(String soPhieuDV);
}
