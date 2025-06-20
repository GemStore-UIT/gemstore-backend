package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.request.CTPhieuBanHangRequest;
import com.gemstore.gemstone_store.dto.request.PhieuBanHangRequest;
import com.gemstore.gemstone_store.dto.response.PhieuBanHangResponse;
import com.gemstore.gemstone_store.mapper.PhieuBanHangMapper;
import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import com.gemstore.gemstone_store.repository.CTPhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.PhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
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
public class PhieuBanHangServiceImpl implements PhieuBanHangService {

    @Autowired
    private PhieuBanHangRepository repo;

    @Autowired
    private CTPhieuBanHangRepository ctRepo;
    @Autowired
    private SanPhamRepository spRepo;

    @Override
    public List<PhieuBanHangResponse> getAll() {
        log.info("Lấy tất cả phiếu bán hàng");
        List<PhieuBanHang> list = repo.findAll();
        log.debug("Số lượng phiếu bán hàng trả về: {}", list.size());
        return list.stream()
                .map(PhieuBanHangMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PhieuBanHangResponse> getById(UUID id) {
        log.info("Tìm phiếu bán hàng với id={}", id);
        Optional<PhieuBanHang> pbh = repo.findById(id);
        if (pbh.isEmpty()) {
            log.warn("Không tìm thấy phiếu bán hàng với id={}", id);
        }
        return pbh.map(PhieuBanHangMapper::toDto);
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
    @Transactional
    public PhieuBanHangResponse saveWithCT(PhieuBanHangRequest req) {
        PhieuBanHang pbh;
        boolean isUpdate = req.getSoPhieuBH() != null && repo.existsById(req.getSoPhieuBH());

        if (isUpdate) {
            pbh = repo.findById(req.getSoPhieuBH())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu bán hàng để cập nhật"));

            pbh.setKhachHang(req.getKhachHang());

            for(CTPhieuBanHang oldCT : pbh.getChiTiet()){
                SanPham sanPham = oldCT.getSanPham();
                if(sanPham != null) sanPham.setTonKho(sanPham.getTonKho() + oldCT.getSoLuong());
            }

            pbh.getChiTiet().clear();
        } else {
            pbh = new PhieuBanHang();
            pbh.setNgayLap(LocalDateTime.now());
            pbh.setKhachHang(req.getKhachHang());
            pbh.setChiTiet(new ArrayList<>());

            pbh = repo.save(pbh);
        }

        int tongTien = 0;

        for (CTPhieuBanHangRequest ctReq : req.getChiTiet()) {
            SanPham sp = spRepo.findById(ctReq.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + ctReq.getMaSanPham()));


            if (sp.getTonKho() < ctReq.getSoLuong()) {
                throw new IllegalArgumentException("Không đủ tồn kho cho sản phẩm: " + sp.getTenSanPham());
            }

            CTPhieuBanHang ct = new CTPhieuBanHang();
            ct.setSanPham(sp);
            ct.setSoLuong(ctReq.getSoLuong());
            ct.setPhieuBanHang(pbh);

            double tyLeLN = sp.getLoaiSanPham().getLoiNhuan() / 100.0;
            int thanhTien = (int) Math.ceil((sp.getDonGia() * ct.getSoLuong() * (1 + tyLeLN)));
            ct.setThanhTien(thanhTien);
            tongTien += thanhTien;

            sp.setTonKho(sp.getTonKho() - ct.getSoLuong());

            ct.setId(new CTPhieuBanHangId(sp.getMaSanPham(), pbh.getSoPhieuBH()));

            pbh.getChiTiet().add(ct);
        }

        pbh.setTongTien(tongTien);

        pbh = repo.save(pbh);

        return PhieuBanHangMapper.toDto(pbh);
    }



    @Override
    public void delete(UUID id) {
        log.info("Xóa phiếu bán hàng với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công phiếu bán hàng với id={}", id);
    }

    @Override
    public void updateTongTien(UUID soPhieuBH){
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
