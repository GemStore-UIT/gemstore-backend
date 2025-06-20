package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.request.CTPhieuMuaHangRequest;
import com.gemstore.gemstone_store.dto.request.PhieuMuaHangRequest;
import com.gemstore.gemstone_store.dto.response.PhieuMuaHangResponse;
import com.gemstore.gemstone_store.mapper.PhieuMuaHangMapper;
import com.gemstore.gemstone_store.model.*;
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
    @Transactional
    public PhieuMuaHang save(PhieuMuaHang pmh) {
        log.info("Lưu phiếu mua hàng: {}", pmh);

        boolean isUpdate = pmh.getSoPhieuMH() != null && repo.existsById(pmh.getSoPhieuMH());

        PhieuMuaHang target = isUpdate
                ? repo.findById(pmh.getSoPhieuMH()).orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mua hàng!"))
                : pmh;

        target.setNgayLap(isUpdate ? pmh.getNgayLap() : LocalDateTime.now());
        target.setNhaCungCap(pmh.getNhaCungCap());
        target.setTongTien(pmh.getTongTien());

        if (isUpdate) {
            target.getChiTiet().clear();
        }

        if (pmh.getChiTiet() != null) {
            for (CTPhieuMuaHang ct : pmh.getChiTiet()) {
                // Lấy maSanPham an toàn
                UUID maSanPham;
                if (ct.getSanPham() != null && ct.getSanPham().getMaSanPham() != null) {
                    maSanPham = ct.getSanPham().getMaSanPham();
                } else if (ct.getId() != null && ct.getId().getMaSanPham() != null) {
                    maSanPham = ct.getId().getMaSanPham();
                } else {
                    maSanPham = null;
                    throw new IllegalArgumentException("Chi tiết phải có mã sản phẩm (maSanPham)!");
                }

                // Lấy sản phẩm từ DB
                SanPham sp = spRepo.findById(maSanPham)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + maSanPham));

                ct.setSanPham(sp);
                ct.setPhieuMuaHang(target);
                ct.setId(new CTPhieuMuaHangId(sp.getMaSanPham(), target.getSoPhieuMH()));

                target.getChiTiet().add(ct);
            }
        }

        PhieuMuaHang saved = repo.save(target);
        log.info("Lưu thành công phiếu mua hàng với id={}", saved.getSoPhieuMH());
        return saved;
    }

    @Override
    @Transactional
    public PhieuMuaHangResponse saveWithCT(PhieuMuaHangRequest req) {
        PhieuMuaHang pmh;

        boolean isUpdate = req.getSoPhieuMH() != null && repo.existsById(req.getSoPhieuMH());

        if (isUpdate) {
            pmh = repo.findById(req.getSoPhieuMH())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mua hàng để cập nhật"));

            NhaCungCap ncc = nccRepo.findById(req.getNhaCungCap())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà cung cấp: " + req.getNhaCungCap()));
            pmh.setNhaCungCap(ncc);

            for(CTPhieuMuaHang oldCT : pmh.getChiTiet()){
                SanPham sanPham = oldCT.getSanPham();
                if(sanPham != null) sanPham.setTonKho(sanPham.getTonKho() + oldCT.getSoLuong());
            }

            pmh.getChiTiet().clear();
        } else {
            pmh = new PhieuMuaHang();
            pmh.setNgayLap(LocalDateTime.now());
            pmh.setChiTiet(new ArrayList<>());

            NhaCungCap ncc = nccRepo.findById(req.getNhaCungCap())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà cung cấp: " + req.getNhaCungCap()));
            pmh.setNhaCungCap(ncc);

            pmh = repo.save(pmh);
        }

        int tongTien = 0;

        for (CTPhieuMuaHangRequest ctReq : req.getChiTiet()) {
            SanPham sp = spRepo.findById(ctReq.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + ctReq.getMaSanPham()));

            CTPhieuMuaHang ct = new CTPhieuMuaHang();
            ct.setSanPham(sp);
            ct.setSoLuong(ctReq.getSoLuong());

            int thanhTien = sp.getDonGia() * ctReq.getSoLuong();
            ct.setThanhTien(thanhTien);
            tongTien += thanhTien;

            sp.setTonKho(sp.getTonKho() + ct.getSoLuong());

            ct.setPhieuMuaHang(pmh);
            ct.setId(new CTPhieuMuaHangId(sp.getMaSanPham(), pmh.getSoPhieuMH()));

            pmh.getChiTiet().add(ct);
        }

        pmh.setTongTien(tongTien);

        pmh = repo.save(pmh);

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
