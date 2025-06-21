package com.gemstore.gemstone_store.repository;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CTPhieuMuaHangRepository extends JpaRepository<CTPhieuMuaHang, CTPhieuMuaHangId> {
    List<CTPhieuMuaHang> findByPhieuMuaHang_SoPhieuMH(UUID soPhieuMuaHang);

    @Query("SELECT COALESCE(SUM(ct.soLuong), 0) FROM CTPhieuMuaHang AS ct WHERE ct.sanPham.maSanPham = :maSanPham AND MONTH(ct.phieuMuaHang.ngayLap) = :thang AND YEAR(ct.phieuMuaHang.ngayLap) = :nam")
    int tongSanPhamMuaVaoTheoThang(@Param("thang") int thang, @Param("nam") int nam, @Param("maSanPham") UUID maSanPham);
}
