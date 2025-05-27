package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import com.gemstore.gemstone_store.repository.CTPhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.PhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.CTPhieuBanHangService;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<CTPhieuBanHang> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<CTPhieuBanHang> getById(CTPhieuBanHangId id) {
        return repo.findById(id);
    }

    @Override
    public CTPhieuBanHang save(CTPhieuBanHang ct) {
        String maSP = ct.getSanPham().getMaSanPham();
        String soPhieuBH = ct.getPhieuBanHang().getSoPhieuBH();

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

        return saved;
    }

    @Override
    public void delete(CTPhieuBanHangId id) {
        repo.deleteById(id);
    }
}
