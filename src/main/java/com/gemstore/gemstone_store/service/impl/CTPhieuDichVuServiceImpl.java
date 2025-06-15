package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.response.CTPhieuDichVuResponse;
import com.gemstore.gemstone_store.mapper.CTPhieuDichVuMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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
    public List<CTPhieuDichVuResponse> getAll() {
        log.info("Lấy tất cả chi tiết phiếu dịch vụ");
        List<CTPhieuDichVu> list = repo.findAll();
        log.debug("Số lượng chi tiết phiếu dịch vụ trả về: {}", list.size());
        return list.stream()
                .map(CTPhieuDichVuMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CTPhieuDichVuResponse> getById(CTPhieuDichVuId id) {
        log.info("Tìm chi tiết phiếu dịch vụ với id={}", id);
        Optional<CTPhieuDichVu> ct = repo.findById(id);
        if (ct.isEmpty()) {
            log.warn("Không tìm thấy chi tiết phiếu dịch vụ với id={}", id);
        }
        return ct.map(CTPhieuDichVuMapper::toDto);
    }

    @Override
    public List<CTPhieuDichVuResponse> getAllByPhieuDV(UUID soPhieuDV){
        log.info("Tìm tất cả chi tiết của phiếu dịch vụ {}", soPhieuDV);
        List<CTPhieuDichVu> cts = repo.findByPhieuDichVu_SoPhieuDV(soPhieuDV);
        if(cts.isEmpty()){
            log.warn("Không tìm thấy chi tiết phiếu dịch vụ của phiếu {}", soPhieuDV);
        }
        return cts.stream()
                .map(CTPhieuDichVuMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CTPhieuDichVu save(CTPhieuDichVu ct) {
        log.info("Lưu chi tiết phiếu dịch vụ: {}", ct);
        UUID soPhieuDV = ct.getPhieuDichVu().getSoPhieuDV();
        UUID maLDV = ct.getLoaiDichVu().getMaLDV();

        PhieuDichVu pdv = pdvRepo.findById(soPhieuDV).
                orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu dịch vụ: " + soPhieuDV));
        LoaiDichVu ldv = ldvRepo.findById(maLDV).
                orElseThrow(() -> new RuntimeException("Không tìm thấy LoaiDichVu: " + maLDV));

        int donGia = ldv.getDonGia();
        int tyLeTraTruoc = ldv.getTraTruoc();

        int thanhTien = donGia * ct.getSoLuong();
        int traTruoc = ct.getTraTruoc();

        if (traTruoc < (thanhTien * tyLeTraTruoc / 100)) {
            throw new IllegalArgumentException("Số tiền trả trước không đủ tối thiểu " + tyLeTraTruoc + "%");
        }

        ct.setDonGia(donGia);
        ct.setThanhTien(thanhTien);
        ct.setConLai(thanhTien - traTruoc);

        ct.setPhieuDichVu(pdv);
        ct.setLoaiDichVu(ldv);
        ct.setId(new CTPhieuDichVuId(soPhieuDV, maLDV));

        CTPhieuDichVu saved = repo.save(ct);

        phieuDichVuService.updateTongTien(soPhieuDV);

        log.info("Lưu chi tiết phiếu dịch vụ thành công với id={}", ct.getId());
        return saved;
    }

    @Override
    public void delete(CTPhieuDichVuId id) {
        log.info("Xóa chi tiết phiếu dịch vụ với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công chi tiết phiếu dịch vụ với id={}", id);
    }

}
