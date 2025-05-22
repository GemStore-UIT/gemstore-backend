package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import com.gemstore.gemstone_store.repository.CTPhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.PhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.CTPhieuMuaHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CTPhieuMuaHangServiceImpl implements CTPhieuMuaHangService {

    @Autowired
    private CTPhieuMuaHangRepository repo;

    @Autowired
    private SanPhamRepository spRepo;

    @Autowired
    private PhieuMuaHangRepository pmhRepo;

    @Override
    public List<CTPhieuMuaHang> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<CTPhieuMuaHang> getById(CTPhieuMuaHangId id) {
        return repo.findById(id);
    }

    @Override
    public CTPhieuMuaHang save(CTPhieuMuaHang ct) {
        String maSP = ct.getSanPham().getMaSanPham();
        String soPhieuMH = ct.getPhieuMuaHang().getSoPhieuMH();

        SanPham sp = spRepo.findById(maSP)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + maSP));
        PhieuMuaHang pmh = pmhRepo.findById(soPhieuMH)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mua hàng: " + soPhieuMH));

        ct.setSanPham(sp);
        ct.setPhieuMuaHang(pmh);
        ct.setId(new CTPhieuMuaHangId(maSP, soPhieuMH));

        return repo.save(ct);
    }

    @Override
    public void delete(CTPhieuMuaHangId id) {
        repo.deleteById(id);
    }
}