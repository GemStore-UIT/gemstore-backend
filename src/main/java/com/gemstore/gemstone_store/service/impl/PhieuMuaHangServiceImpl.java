package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.repository.CTPhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.PhieuMuaHangRepository;
import com.gemstore.gemstone_store.service.PhieuMuaHangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PhieuMuaHangServiceImpl implements PhieuMuaHangService {

    @Autowired
    private PhieuMuaHangRepository repo;

    @Autowired
    private CTPhieuMuaHangRepository ctRepo;

    @Override
    public List<PhieuMuaHang> getAll() {
        log.info("Lấy tất cả phiếu mua hàng");
        List<PhieuMuaHang> list = repo.findAll();
        log.debug("Số lượng phiếu mua hàng trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<PhieuMuaHang> getById(String id) {
        log.info("Tìm phiếu mua hàng với id={}", id);
        Optional<PhieuMuaHang> pmh = repo.findById(id);
        if (pmh.isEmpty()) {
            log.warn("Không tìm thấy phiếu mua hàng với id={}", id);
        }
        return pmh;
    }

    @Override
    public PhieuMuaHang save(PhieuMuaHang pmh) {
        log.info("Lưu phiếu mua hàng: {}", pmh);
        if (!repo.existsById(pmh.getSoPhieuMH())) {
            pmh.setNgayLap(LocalDateTime.now());
        }
        PhieuMuaHang saved = repo.save(pmh);
        log.info("Lưu thành công phiếu mua hàng với id={}", saved.getSoPhieuMH());
        return saved;
    }

    @Override
    public void delete(String id) {
        log.info("Xóa phiếu mua hàng với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công phiếu mua hàng với id={}", id);
    }

    @Override
    public void updateTongTien(String soPhieuMH){
        List<CTPhieuMuaHang> ct = ctRepo.findByPhieuMuaHang_SoPhieuMH(soPhieuMH);

        int tongTien = 0;
        for(var c : ct){
            tongTien += c.getThanhTien();
        }

        Optional<PhieuMuaHang> opt = repo.findById(soPhieuMH);
        if(opt.isPresent()){
            PhieuMuaHang pmh = opt.get();
            pmh.setTongTien(tongTien);
            repo.save(pmh);
            log.info("Cập nhật tổng tiền phiếu mua hàng id={} thành {}", soPhieuMH, tongTien);
        }
    }
}
