package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CTPhieuBanHangRepository extends JpaRepository<CTPhieuBanHang, CTPhieuBanHangId> {
    List<CTPhieuBanHang> findByPhieuBanHang_SoPhieuBH(UUID soPhieuBH);

    @Query("SELECT COALESCE(SUM(ct.soLuong), 0) FROM CTPhieuBanHang AS ct WHERE ct.sanPham.maSanPham = :maSanPham AND MONTH(ct.phieuBanHang.ngayLap) = :thang AND YEAR(ct.phieuBanHang.ngayLap) = :nam")
    int tongSanPhamBanRaTheoThang(@Param("thang") int thang, @Param("nam") int nam, @Param("maSanPham") UUID maSanPham);
}
