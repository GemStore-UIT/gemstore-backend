package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.repository.CTPhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.PhieuBanHangRepository;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PhieuBanHangServiceImpl implements PhieuBanHangService {

    @Autowired
    private PhieuBanHangRepository repo;

    @Autowired
    private CTPhieuBanHangRepository ctRepo;

    @Override
    public List<PhieuBanHang> getAll() {
        log.info("Lấy tất cả phiếu bán hàng");
        List<PhieuBanHang> list = repo.findAll();
        log.debug("Số lượng phiếu bán hàng trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<PhieuBanHang> getById(String id) {
        log.info("Tìm phiếu bán hàng với id={}", id);
        Optional<PhieuBanHang> pbh = repo.findById(id);
        if (pbh.isEmpty()) {
            log.warn("Không tìm thấy phiếu bán hàng với id={}", id);
        }
        return pbh;
    }

    @Override
    public PhieuBanHang save(PhieuBanHang pbh) {
        log.info("Lưu phiếu bán hàng: {}", pbh);
        if (!repo.existsById(pbh.getSoPhieuBH())) {
            pbh.setNgayLap(LocalDateTime.now());
        }
        PhieuBanHang saved = repo.save(pbh);
        log.info("Lưu thành công phiếu bán hàng với id={}", saved.getSoPhieuBH());
        return saved;
    }

    @Override
    public void delete(String id) {
        log.info("Xóa phiếu bán hàng với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công phiếu bán hàng với id={}", id);
    }

    @Override
    public void updateTongTien(String soPhieuBH){
        List<CTPhieuBanHang> dsChiTiet = ctRepo.findByPhieuBanHang_SoPhieuBH(soPhieuBH);

        int tongTien = 0;
        for(var ct : dsChiTiet){
            tongTien += ct.getThanhTien();
        }

        Optional<PhieuBanHang> opt = repo.findById(soPhieuBH);
        if(opt.isPresent()){
            PhieuBanHang pbh = opt.get();
            pbh.setTongTien(tongTien);
            repo.save(pbh);
            log.info("Cập nhật tổng tiền phiếu bán hàng id={} thành {}", soPhieuBH, tongTien);
        }
    }
}
