package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.dto.response.BaoCaoResponse;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.repository.CTPhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.CTPhieuMuaHangRepository;
import com.gemstore.gemstone_store.service.BaoCaoService;
import com.gemstore.gemstone_store.service.SanPhamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BaoCaoServiceImpl implements BaoCaoService {

    @Autowired
    SanPhamService sanPhamService;

    @Autowired
    CTPhieuBanHangRepository ctPhieuBanHangRepository;

    @Autowired
    CTPhieuMuaHangRepository ctPhieuMuaHangRepository;

    @Override
    public List<BaoCaoResponse> getBaoCao(int thang, int nam){
        log.info("Lấy báo cáo tháng={}, nam={}", thang, nam);
        List<BaoCaoResponse> responses = new ArrayList<>();
        List<SanPham> sanPhams = sanPhamService.getAll();
        for(SanPham sanPham : sanPhams){
            BaoCaoResponse baoCao = new BaoCaoResponse();
            int muaVao = ctPhieuMuaHangRepository.tongSanPhamMuaVaoTheoThang(thang, nam, sanPham.getMaSanPham());
            int banRa = ctPhieuBanHangRepository.tongSanPhamBanRaTheoThang(thang, nam, sanPham.getMaSanPham());

            baoCao.setMaSanPham(sanPham.getMaSanPham());
            baoCao.setTenSanPham(sanPham.getTenSanPham());
            baoCao.setTonDau(sanPham.getTonKho() - muaVao + banRa);
            baoCao.setMuaVao(muaVao);
            baoCao.setBanRa(banRa);
            baoCao.setDonViTinh(sanPham.getLoaiSanPham().getDonViTinh().getTenDonVi());
            baoCao.setTonCuoi(sanPham.getTonKho());

            responses.add(baoCao);
        }
        return responses;
    }

}
