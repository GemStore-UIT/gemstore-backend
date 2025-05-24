package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import com.gemstore.gemstone_store.repository.CTPhieuDichVuRepository;
import com.gemstore.gemstone_store.repository.LoaiDichVuRepository;
import com.gemstore.gemstone_store.repository.PhieuDichVuRepository;
import com.gemstore.gemstone_store.service.CTPhieuDichVuService;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CTPhieuDichVuServiceImpl implements CTPhieuDichVuService {

    @Autowired
    private CTPhieuDichVuRepository repo;

    @Autowired
    private PhieuDichVuRepository pdvRepo;

    @Autowired
    private LoaiDichVuRepository ldvRepo;

    @Autowired
    private PhieuDichVuService phieuDichVuService;

    @Override
    public List<CTPhieuDichVu> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<CTPhieuDichVu> getById(CTPhieuDichVuId id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(CTPhieuDichVu ct) {
        try {
            String soPhieuDV = ct.getPhieuDichVu().getSoPhieuDV();
            String maLDV = ct.getLoaiDichVu().getMaLDV();

            PhieuDichVu pdv = pdvRepo.findById(soPhieuDV).
                    orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu dịch vụ: " + soPhieuDV));
            LoaiDichVu ldv = ldvRepo.findById(maLDV).
                    orElseThrow(() -> new RuntimeException("Không tìm thấy LoaiDichVu: " + maLDV));

            int donGia = ldv.getDonGia();
            int tyLeTraTruoc = ldv.getTraTruoc();

            int thanhTien = donGia * ct.getSoLuong();
            int traTruoc = ct.getTraTruoc();

            if (traTruoc < (thanhTien * tyLeTraTruoc / 100)) {
                throw new Exception("Số tiền trả trước không đủ tối thiểu " + tyLeTraTruoc + "%");
            }

            ct.setDonGia(donGia);
            ct.setThanhTien(thanhTien);
            ct.setConLai(thanhTien - traTruoc);

            ct.setPhieuDichVu(pdv);
            ct.setLoaiDichVu(ldv);
            ct.setId(new CTPhieuDichVuId(soPhieuDV, maLDV));

            phieuDichVuService.updateTongTien(soPhieuDV);

            CTPhieuDichVu saved = repo.save(ct);

            return ResponseEntity.status(
                    ct.getId() == null ? HttpStatus.CREATED : HttpStatus.OK
            ).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public void delete(CTPhieuDichVuId id) {
        repo.deleteById(id);
    }

}
