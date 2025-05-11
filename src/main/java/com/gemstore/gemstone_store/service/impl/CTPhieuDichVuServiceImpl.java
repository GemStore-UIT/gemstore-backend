package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import com.gemstore.gemstone_store.repository.CTPhieuDichVuRepository;
import com.gemstore.gemstone_store.repository.LoaiDichVuRepository;
import com.gemstore.gemstone_store.service.CTPhieuDichVuService;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CTPhieuDichVuServiceImpl implements CTPhieuDichVuService {

    @Autowired
    private CTPhieuDichVuRepository repo;

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
    public CTPhieuDichVu save(CTPhieuDichVu ct) throws Exception {
        String maLDV = ct.getId().getMaLDV();

        var ldvOpt = ldvRepo.findById(maLDV);
        if (ldvOpt.isEmpty()) {
            throw new Exception("Không tìm thấy loại dịch vụ: " + maLDV);
        }

        var ldv = ldvOpt.get();
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
        repo.save(ct);

        phieuDichVuService.updateTongTien(ct.getId().getSoPhieuDV());

        return ct;
    }

    @Override
    public void delete(CTPhieuDichVuId id) {
        repo.deleteById(id);
    }
}
