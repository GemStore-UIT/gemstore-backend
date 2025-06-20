package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.request.CTPhieuDichVuRequest;
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
    public CTPhieuDichVuResponse save(CTPhieuDichVuRequest ctReq) {
        log.info("Lưu/Update chi tiết phiếu dịch vụ: {}", ctReq);
        UUID soPhieuDV = ctReq.getSoPhieuDV();
        UUID maLDV = ctReq.getMaLDV();

        if (soPhieuDV == null || maLDV == null)
            throw new IllegalArgumentException("Thiếu mã phiếu dịch vụ hoặc mã loại dịch vụ!");

        PhieuDichVu pdv = pdvRepo.findById(soPhieuDV)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu dịch vụ: " + soPhieuDV));
        LoaiDichVu ldv = ldvRepo.findById(maLDV)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy LoaiDichVu: " + maLDV));

        int donGia = ldv.getDonGia();
        float tyLeTraTruoc = ldv.getTraTruoc();

        CTPhieuDichVu ct = repo.findById(new CTPhieuDichVuId(soPhieuDV, maLDV)).orElse(null);

        boolean isNew = false;
        if (ct == null) {
            ct = new CTPhieuDichVu();
            ct.setId(new CTPhieuDichVuId(soPhieuDV, maLDV));
            ct.setPhieuDichVu(pdv);
            ct.setLoaiDichVu(ldv);
            isNew = true;
        }

        if (isNew && (ctReq.getSoLuong() == null || ctReq.getTraTruoc() == null))
            throw new IllegalArgumentException("Tạo mới cần có số lượng và trả trước");

        if (ctReq.getSoLuong() != null) ct.setSoLuong(ctReq.getSoLuong());
        if (ctReq.getTraTruoc() != null) ct.setTraTruoc(ctReq.getTraTruoc());
        if (ctReq.getNgayGiao() != null) ct.setNgayGiao(ctReq.getNgayGiao());
        if (ctReq.getTinhTrang() != null) ct.setTinhTrang(ctReq.getTinhTrang());

        if (ct.getSoLuong() == null) throw new IllegalArgumentException("Thiếu số lượng");
        if (ct.getTraTruoc() == null) throw new IllegalArgumentException("Thiếu trả trước");

        int thanhTien = donGia * ct.getSoLuong();
        int traTruoc = ct.getTraTruoc();

        int minTraTruoc = Math.round(thanhTien * tyLeTraTruoc / 100f);
        if (traTruoc < minTraTruoc) {
            throw new IllegalArgumentException("Số tiền trả trước phải tối thiểu " + tyLeTraTruoc + "% (≥ " + minTraTruoc + ")");
        }
        if (traTruoc > thanhTien) {
            throw new IllegalArgumentException("Số tiền trả trước không được vượt quá thành tiền (" + thanhTien + ")");
        }

        ct.setDonGia(donGia);
        ct.setThanhTien(thanhTien);
        ct.setConLai(thanhTien - traTruoc);

        CTPhieuDichVu saved = repo.save(ct);

        phieuDichVuService.updateTongTien(soPhieuDV);

        log.info("Lưu chi tiết phiếu dịch vụ thành công với id={}", ct.getId());
        return CTPhieuDichVuMapper.toDto(saved);
    }



    @Override
    public void delete(CTPhieuDichVuId id) {
        log.info("Xóa chi tiết phiếu dịch vụ với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công chi tiết phiếu dịch vụ với id={}", id);
    }

}
