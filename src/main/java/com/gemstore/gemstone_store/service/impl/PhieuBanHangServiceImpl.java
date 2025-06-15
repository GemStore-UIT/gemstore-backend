package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.request.CTPhieuBanHangRequest;
import com.gemstore.gemstone_store.dto.request.PhieuBanHangRequest;
import com.gemstore.gemstone_store.dto.response.PhieuBanHangResponse;
import com.gemstore.gemstone_store.mapper.PhieuBanHangMapper;
import com.gemstore.gemstone_store.mapper.PhieuMuaHangMapper;
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
        PhieuBanHang pbh = new PhieuBanHang();
        pbh.setKhachHang(req.getKhachHang());
        pbh.setNgayLap(LocalDateTime.now());

        List<CTPhieuBanHang> chiTietList = new ArrayList<>();
        for (CTPhieuBanHangRequest ctReq : req.getChiTiet()) {
            CTPhieuBanHang ct = new CTPhieuBanHang();

            SanPham sp = spRepo.findById(ctReq.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + ctReq.getMaSanPham()));

            ct.setSanPham(sp);
            ct.setSoLuong(ctReq.getSoLuong());
            ct.setPhieuBanHang(pbh);
            ct.setThanhTien((int)(sp.getDonGia() * ct.getSoLuong() * (1 + sp.getLoaiSanPham().getLoiNhuan() / 100.0)));

            CTPhieuBanHangId id = new CTPhieuBanHangId(sp.getMaSanPham(), pbh.getSoPhieuBH());
            ct.setId(id);

            chiTietList.add(ct);
        }
        pbh.setChiTiet(chiTietList);

        repo.save(pbh);

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
