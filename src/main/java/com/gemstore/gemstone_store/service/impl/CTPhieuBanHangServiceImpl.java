package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.response.CTPhieuBanHangResponse;
import com.gemstore.gemstone_store.mapper.CTPhieuBanHangMapper;
import com.gemstore.gemstone_store.mapper.PhieuBanHangMapper;
import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import com.gemstore.gemstone_store.repository.CTPhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.PhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.CTPhieuBanHangService;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CTPhieuBanHangServiceImpl implements CTPhieuBanHangService {

    @Autowired
    private CTPhieuBanHangRepository repo;

    @Autowired
    private SanPhamRepository spRepo;

    @Autowired
    private PhieuBanHangRepository pbhRepo;

    @Autowired
    private PhieuBanHangService pbhService;

    @Override
    public List<CTPhieuBanHangResponse> getAll() {
        log.info("Lấy tất cả chi tiết phiếu bán hàng");
        List<CTPhieuBanHang> list = repo.findAll();
        log.debug("Số lượng chi tiết phiếu bán hàng trả về: {}", list.size());
        return list.stream()
                .map(CTPhieuBanHangMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CTPhieuBanHangResponse> getById(CTPhieuBanHangId id) {
        log.info("Tìm chi tiết phiếu bán hàng với id={}", id);
        Optional<CTPhieuBanHang> ct = repo.findById(id);
        if (ct.isEmpty()) {
            log.warn("Không tìm thấy chi tiết phiếu bán hàng với id={}", id);
        }
        return ct.map(CTPhieuBanHangMapper::toDto);
    }

    @Override
    public List<CTPhieuBanHang> getAllByPhieuBH(UUID soPhieuBH){
        log.info("Tìm chi tiết phiếu bán hàng của phiếu bán hàng {}", soPhieuBH);
        List<CTPhieuBanHang> cts = repo.findByPhieuBanHang_SoPhieuBH(soPhieuBH);
        if (cts.isEmpty()) {
            log.warn("Không tìm thấy chi tiết phiếu bán hàng của phiếu {}", soPhieuBH);
        }
        return cts;
    }

    @Override
    public CTPhieuBanHang save(CTPhieuBanHang ct) {
        log.info("Lưu chi tiết phiếu bán hàng: {}", ct);
        UUID maSP = ct.getSanPham().getMaSanPham();
        UUID soPhieuBH = ct.getPhieuBanHang().getSoPhieuBH();

        SanPham sp = spRepo.findById(maSP)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + maSP));
        PhieuBanHang pbh = pbhRepo.findById(soPhieuBH)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu bán hàng: " + soPhieuBH));

        ct.setSanPham(sp);
        ct.setPhieuBanHang(pbh);
        ct.setId(new CTPhieuBanHangId(maSP, soPhieuBH));
        ct.setThanhTien((int)(sp.getDonGia() * ct.getSoLuong() * (1 + sp.getLoaiSanPham().getLoiNhuan()/100)));

        CTPhieuBanHang saved = repo.save(ct);

        pbhService.updateTongTien(soPhieuBH);

        log.info("Lưu chi tiết phiếu bán hàng thành công với id={}", ct.getId());
        return saved;
    }

    @Override
    public void delete(CTPhieuBanHangId id) {
        log.info("Xóa chi tiết phiếu bán hàng với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công chi tiết phiếu bán hàng với id={}", id);
    }
}
