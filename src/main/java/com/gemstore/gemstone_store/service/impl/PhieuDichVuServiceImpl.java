package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.request.CTPhieuDichVuRequest;
import com.gemstore.gemstone_store.dto.request.PhieuDichVuRequest;
import com.gemstore.gemstone_store.dto.response.PhieuDichVuResponse;
import com.gemstore.gemstone_store.mapper.PhieuDichVuMapper;
import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import com.gemstore.gemstone_store.repository.CTPhieuDichVuRepository;
import com.gemstore.gemstone_store.repository.LoaiDichVuRepository;
import com.gemstore.gemstone_store.repository.PhieuDichVuRepository;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PhieuDichVuServiceImpl implements PhieuDichVuService {

    @Autowired
    private PhieuDichVuRepository repo;

    @Autowired
    private CTPhieuDichVuRepository ctRepo;
    @Autowired
    private LoaiDichVuRepository ldvRepo;

    @Override
    public List<PhieuDichVuResponse> getAll() {
        log.info("Lấy tất cả phiếu dịch vụ");
        List<PhieuDichVu> list = repo.findAll();
        log.debug("Số lượng phiếu dịch vụ trả về: {}", list.size());
        return list.stream()
                .map(PhieuDichVuMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PhieuDichVuResponse> getById(UUID id) {
        log.info("Tìm phiếu dịch vụ với id={}", id);
        Optional<PhieuDichVu> pdv = repo.findById(id);
        if (pdv.isEmpty()) {
            log.warn("Không tìm thấy phiếu dịch vụ với id={}", id);
        }
        return pdv.map(PhieuDichVuMapper::toDto);
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
    @Transactional
    public PhieuDichVuResponse saveWithCT(PhieuDichVuRequest req) {
        PhieuDichVu pdv;
        boolean isUpdate = req.getSoPhieuDV() != null && repo.existsById(req.getSoPhieuDV());

        if (isUpdate) {
            pdv = repo.findById(req.getSoPhieuDV())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu dịch vụ để cập nhật"));

            pdv.setKhachHang(req.getKhachHang());
            pdv.setSdt(req.getSdt());

            pdv.getChiTiet().clear();

        } else {
            pdv = new PhieuDichVu();
            pdv.setNgayLap(LocalDateTime.now());
            pdv.setKhachHang(req.getKhachHang());
            pdv.setSdt(req.getSdt());
            pdv.setChiTiet(new ArrayList<>());

            pdv = repo.save(pdv);
        }

        int tongTien = 0;
        int tongTraTruoc = 0;
        int tongConLai = 0;

        for (CTPhieuDichVuRequest ctReq : req.getChiTiet()) {
            LoaiDichVu ldv = ldvRepo.findById(ctReq.getMaLDV())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy loại dịch vụ: " + ctReq.getMaLDV()));

            int donGia = ldv.getDonGia();
            float tyLeTraTruoc = ldv.getTraTruoc();
            int soLuong = ctReq.getSoLuong();
            int thanhTien = donGia * soLuong;
            int traTruoc = ctReq.getTraTruoc();

            int minTraTruoc = (int) Math.ceil(thanhTien * tyLeTraTruoc / 100f);
            if (traTruoc < minTraTruoc) {
                throw new IllegalArgumentException("Trả trước của dịch vụ [" + ldv.getTenLDV() + "] phải tối thiểu " + tyLeTraTruoc + "% (≥ " + minTraTruoc + ")");
            }
            if (traTruoc > thanhTien) {
                throw new IllegalArgumentException("Trả trước của dịch vụ [" + ldv.getTenLDV() + "] không vượt quá thành tiền (" + thanhTien + ")");
            }

            CTPhieuDichVu ct = new CTPhieuDichVu();
            ct.setLoaiDichVu(ldv);
            ct.setDonGia(donGia);
            ct.setSoLuong(soLuong);
            ct.setThanhTien(thanhTien);
            ct.setTraTruoc(traTruoc);
            ct.setConLai(thanhTien - traTruoc);
            ct.setNgayGiao(ctReq.getNgayGiao());
            ct.setTinhTrang(ctReq.getTinhTrang());
            ct.setPhieuDichVu(pdv);
            ct.setId(new CTPhieuDichVuId(pdv.getSoPhieuDV(), ldv.getMaLDV()));

            tongTien += thanhTien;
            tongTraTruoc += traTruoc;
            tongConLai += (thanhTien - traTruoc);

            pdv.getChiTiet().add(ct);
        }

        pdv.setTongTien(tongTien);
        pdv.setTongTienTraTruoc(tongTraTruoc);
        pdv.setTongTienConLai(tongConLai);

        boolean isHoanThanh = pdv.getChiTiet().stream()
                .allMatch(ct -> "Đã giao".equals(ct.getTinhTrang()));
        pdv.setTinhTrang(isHoanThanh ? "Hoàn thành" : "Chưa hoàn thành");

        pdv = repo.save(pdv);
        return PhieuDichVuMapper.toDto(pdv);
    }



    @Override
    public void delete(UUID id) {
        log.info("Xóa phiếu dịch vụ với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công phiếu dịch vụ với id={}", id);
    }

    @Override
    public void updateTongTien(UUID soPhieuDV) {
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
