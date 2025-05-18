package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.repository.CTPhieuDichVuRepository;
import com.gemstore.gemstone_store.repository.PhieuDichVuRepository;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuDichVuServiceImpl implements PhieuDichVuService {

    @Autowired
    private PhieuDichVuRepository repo;

    @Autowired
    private CTPhieuDichVuRepository ctRepo;

    @Override
    public List<PhieuDichVu> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<PhieuDichVu> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public PhieuDichVu save(PhieuDichVu pdv) {
        if (!repo.existsById(pdv.getSoPhieuDV())) {
            pdv.setNgayLap(LocalDateTime.now());
        }
        return repo.save(pdv);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
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
        }
    }
}
