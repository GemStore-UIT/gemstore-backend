package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import com.gemstore.gemstone_store.repository.CTPhieuBanHangRepository;
import com.gemstore.gemstone_store.service.CTPhieuBanHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CTPhieuBanHangServiceImpl implements CTPhieuBanHangService {

    @Autowired
    private CTPhieuBanHangRepository repo;

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
        return repo.save(ct);
    }

    @Override
    public void delete(CTPhieuBanHangId id) {
        repo.deleteById(id);
    }
}
