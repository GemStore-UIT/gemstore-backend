package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.request.CTPhieuMuaHangRequest;
import com.gemstore.gemstone_store.dto.request.PhieuMuaHangRequest;
import com.gemstore.gemstone_store.dto.response.PhieuMuaHangResponse;
import com.gemstore.gemstone_store.mapper.PhieuMuaHangMapper;
import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.NhaCungCap;
import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import com.gemstore.gemstone_store.repository.CTPhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.NhaCungCapRepository;
import com.gemstore.gemstone_store.repository.PhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.PhieuMuaHangService;
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
public class PhieuMuaHangServiceImpl implements PhieuMuaHangService {

    @Autowired
    private PhieuMuaHangRepository repo;

    @Autowired
    private CTPhieuMuaHangRepository ctRepo;
    @Autowired
    private SanPhamRepository spRepo;
    @Autowired
    private NhaCungCapRepository nccRepo;

    @Override
    public List<PhieuMuaHangResponse> getAll() {
        log.info("Lấy tất cả phiếu mua hàng");
        List<PhieuMuaHang> list = repo.findAll();
        log.debug("Số lượng phiếu mua hàng trả về: {}", list.size());
        return list.stream().map(PhieuMuaHangMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<PhieuMuaHangResponse> getById(UUID id) {
        log.info("Tìm phiếu mua hàng với id={}", id);
        Optional<PhieuMuaHang> pmh = repo.findById(id);
        if (pmh.isEmpty()) {
            log.warn("Không tìm thấy phiếu mua hàng với id={}", id);
        }
        return pmh.map(PhieuMuaHangMapper::toDto);
    }

    @Override
    public PhieuMuaHang save(PhieuMuaHang pmh) {
        log.info("Lưu phiếu mua hàng: {}", pmh);
        if (!repo.existsById(pmh.getSoPhieuMH())) {
            pmh.setNgayLap(LocalDateTime.now());
        }
        PhieuMuaHang saved = repo.save(pmh);
        log.info("Lưu thành công phiếu mua hàng với id={}", saved.getSoPhieuMH());
        return saved;
    }

    @Override
    @Transactional
    public PhieuMuaHangResponse saveWithCT(PhieuMuaHangRequest req) {
        PhieuMuaHang pmh = new PhieuMuaHang();
        pmh.setNgayLap(LocalDateTime.now());

        NhaCungCap ncc = nccRepo.findById(req.getNhaCungCap())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy NCC"));
        pmh.setNhaCungCap(ncc);

        pmh = repo.save(pmh);

        List<CTPhieuMuaHang> ctList = new ArrayList<>();
        int tongTien = 0;
        for (CTPhieuMuaHangRequest ctReq : req.getChiTiet()) {
            CTPhieuMuaHang ct = new CTPhieuMuaHang();

            SanPham sp = spRepo.findById(ctReq.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + ctReq.getMaSanPham()));

            ct.setSanPham(sp);
            ct.setSoLuong(ctReq.getSoLuong());
            ct.setPhieuMuaHang(pmh);

            int thanhTien = sp.getDonGia() * ct.getSoLuong();
            ct.setThanhTien(thanhTien);
            tongTien += thanhTien;

            ct.setId(new CTPhieuMuaHangId(sp.getMaSanPham(), pmh.getSoPhieuMH()));

            ctList.add(ct);
        }
        pmh.setChiTiet(ctList);
        pmh.setTongTien(tongTien);

        repo.save(pmh);

        return PhieuMuaHangMapper.toDto(pmh);
    }

    @Override
    public void delete(UUID id) {
        log.info("Xóa phiếu mua hàng với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công phiếu mua hàng với id={}", id);
    }

    @Override
    public void updateTongTien(UUID soPhieuMH){
        List<CTPhieuMuaHang> ct = ctRepo.findByPhieuMuaHang_SoPhieuMH(soPhieuMH);

        int tongTien = 0;
        for(var c : ct){
            tongTien += c.getThanhTien();
        }

        Optional<PhieuMuaHang> opt = repo.findById(soPhieuMH);
        if(opt.isPresent()){
            PhieuMuaHang pmh = opt.get();
            pmh.setTongTien(tongTien);
            repo.save(pmh);
            log.info("Cập nhật tổng tiền phiếu mua hàng id={} thành {}", soPhieuMH, tongTien);
        }
    }
}
