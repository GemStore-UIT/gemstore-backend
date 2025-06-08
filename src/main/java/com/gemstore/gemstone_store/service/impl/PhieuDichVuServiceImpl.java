package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.repository.CTPhieuDichVuRepository;
import com.gemstore.gemstone_store.repository.PhieuDichVuRepository;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PhieuDichVuServiceImpl implements PhieuDichVuService {

    @Autowired
    private PhieuDichVuRepository repo;

    @Autowired
    private CTPhieuDichVuRepository ctRepo;

    @Override
    public List<PhieuDichVu> getAll() {
        log.info("Lấy tất cả phiếu dịch vụ");
        List<PhieuDichVu> list = repo.findAll();
        log.debug("Số lượng phiếu dịch vụ trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<PhieuDichVu> getById(String id) {
        log.info("Tìm phiếu dịch vụ với id={}", id);
        Optional<PhieuDichVu> pdv = repo.findById(id);
        if (pdv.isEmpty()) {
            log.warn("Không tìm thấy phiếu dịch vụ với id={}", id);
        }
        return pdv;
    }

    @Override
    public PhieuDichVu save(PhieuDichVu pdv) {
        log.info("Lưu phiếu dịch vụ: {}", pdv);
        if (!repo.existsById(pdv.getSoPhieuDV())) {
            pdv.setNgayLap(LocalDateTime.now());
        }
        PhieuDichVu saved = repo.save(pdv);
        log.info("Lưu thành công phiếu dịch vụ với id={}", saved.getSoPhieuDV());
        return saved;
    }

    @Override
    public void delete(String id) {
        log.info("Xóa phiếu dịch vụ với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công phiếu dịch vụ với id={}", id);
    }

    @Override
    public void updateTongTien(String soPhieuDV) {
        List<CTPhieuDichVu> dsChiTiet = ctRepo.findByPhieuDichVu_SoPhieuDV(soPhieuDV);

        int tongTien = 0;
        int tongTraTruoc = 0;

        for (var ct : dsChiTiet) {
            tongTien += ct.getThanhTien();
            tongTraTruoc += ct.getTraTruoc();
        }

        int tongConLai = tongTien - tongTraTruoc;

        boolean isHoanThanh = dsChiTiet.stream()
                .allMatch(ct -> "Đã giao".equals(ct.getTinhTrang()));

        Optional<PhieuDichVu> opt = repo.findById(soPhieuDV);
        if (opt.isPresent()) {
            PhieuDichVu pdv = opt.get();
            pdv.setTongTien(tongTien);
            pdv.setTongTienTraTruoc(tongTraTruoc);
            pdv.setTongTienConLai(tongConLai);
            pdv.setTinhTrang(isHoanThanh ? "Hoàn thành" : "Chưa hoàn thành");
            repo.save(pdv);
            log.info("Cập nhật tổng tiền/tình trạng phiếu dịch vụ {}: tổng tiền={}, đã trả trước={}, còn lại={}, trạng thái={}", soPhieuDV, tongTien, tongTraTruoc, tongConLai, pdv.getTinhTrang());
        }
    }
}
