package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.repository.PhieuMuaHangRepository;
import com.gemstore.gemstone_store.service.PhieuMuaHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhieuMuaHangServiceImpl implements PhieuMuaHangService {

    @Autowired
    private PhieuMuaHangRepository repo;

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
        return repo.save(pmh);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
