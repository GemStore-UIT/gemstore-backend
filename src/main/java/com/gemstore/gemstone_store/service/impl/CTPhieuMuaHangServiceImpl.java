package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import com.gemstore.gemstone_store.repository.CTPhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.PhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.CTPhieuMuaHangService;
import com.gemstore.gemstone_store.service.PhieuMuaHangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CTPhieuMuaHangServiceImpl implements CTPhieuMuaHangService {

    @Autowired
    private CTPhieuMuaHangRepository repo;

    @Autowired
    private SanPhamRepository spRepo;

    @Autowired
    private PhieuMuaHangRepository pmhRepo;

    @Autowired
    private PhieuMuaHangService pmhService;

    @Override
    public List<CTPhieuMuaHang> getAll() {
        log.info("Lấy tất cả chi tiết phiếu mua hàng");
        List<CTPhieuMuaHang> list = repo.findAll();
        log.debug("Số lượng chi tiết phiếu mua hàng trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<CTPhieuMuaHang> getById(CTPhieuMuaHangId id) {
        log.info("Tìm chi tiết phiếu mua hàng với id={}", id);
        Optional<CTPhieuMuaHang> ct = repo.findById(id);
        if (ct.isEmpty()) {
            log.warn("Không tìm thấy chi tiết phiếu mua hàng với id={}", id);
        }
        return ct;
    }

    @Override
    public List<CTPhieuMuaHang> getAllByPhieuMH(UUID soPhieuMH){
        log.info("Tìm chi tiết phiếu bán hàng của phiếu mua hàng {}", soPhieuMH);
        List<CTPhieuMuaHang> cts = repo.findByPhieuMuaHang_SoPhieuMH(soPhieuMH);
        if (cts.isEmpty()) {
            log.warn("Không tìm thấy chi tiết phiếu mua hàng của phiếu {}", soPhieuMH);
        }
        return cts;
    }

    @Override
    public CTPhieuMuaHang save(CTPhieuMuaHang ct) {
        log.info("Lưu chi tiết phiếu mua hàng: {}", ct);
        UUID maSP = ct.getSanPham().getMaSanPham();
        UUID soPhieuMH = ct.getPhieuMuaHang().getSoPhieuMH();

        SanPham sp = spRepo.findById(maSP)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + maSP));
        PhieuMuaHang pmh = pmhRepo.findById(soPhieuMH)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mua hàng: " + soPhieuMH));

        ct.setSanPham(sp);
        ct.setPhieuMuaHang(pmh);
        ct.setId(new CTPhieuMuaHangId(maSP, soPhieuMH));

        CTPhieuMuaHang saved = repo.save(ct);

        pmhService.updateTongTien(soPhieuMH);

        log.info("Lưu chi tiết phiếu mua hàng thành công với id={}", ct.getId());
        return saved;
    }

    @Override
    public void delete(CTPhieuMuaHangId id) {
        log.info("Xóa chi tiết phiếu mua hàng với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công chi tiết phiếu mua hàng với id={}", id);
    }
}