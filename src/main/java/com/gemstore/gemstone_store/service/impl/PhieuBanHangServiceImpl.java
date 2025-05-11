package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.repository.PhieuBanHangRepository;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuBanHangServiceImpl implements PhieuBanHangService {

    @Autowired
    private PhieuBanHangRepository repo;

    @Override
    public List<PhieuBanHang> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<PhieuBanHang> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public PhieuBanHang save(PhieuBanHang pbh) {
        if (!repo.existsById(pbh.getSoPhieuBH())) {
            pbh.setNgayLap(LocalDateTime.now());
        }
        return repo.save(pbh);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
