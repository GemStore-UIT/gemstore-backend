package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import com.gemstore.gemstone_store.repository.CTPhieuMuaHangRepository;
import com.gemstore.gemstone_store.service.CTPhieuMuaHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CTPhieuMuaHangServiceImpl implements CTPhieuMuaHangService {

    @Autowired
    private CTPhieuMuaHangRepository repo;

    @Override
    public List<CTPhieuMuaHang> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<CTPhieuMuaHang> getById(CTPhieuMuaHangId id) {
        return repo.findById(id);
    }

    @Override
    public CTPhieuMuaHang save(CTPhieuMuaHang ct) {
        return repo.save(ct);
    }

    @Override
    public void delete(CTPhieuMuaHangId id) {
        repo.deleteById(id);
    }
}