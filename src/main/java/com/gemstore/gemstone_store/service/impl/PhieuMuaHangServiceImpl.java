package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.repository.CTPhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.PhieuMuaHangRepository;
import com.gemstore.gemstone_store.service.PhieuMuaHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuMuaHangServiceImpl implements PhieuMuaHangService {

    @Autowired
    private PhieuMuaHangRepository repo;

    @Autowired
    private CTPhieuMuaHangRepository ctRepo;

    @Override
    public List<PhieuMuaHang> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<PhieuMuaHang> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public PhieuMuaHang save(PhieuMuaHang pmh) {
        if (!repo.existsById(pmh.getSoPhieuMH())) {
            pmh.setNgayLap(LocalDateTime.now());
        }
        return repo.save(pmh);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    @Override
    public void updateTongTien(String soPhieuMH){
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
        }
    }
}
