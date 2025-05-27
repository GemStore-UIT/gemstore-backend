package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository repo;

    @Override
    public List<SanPham> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<SanPham> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public SanPham save(SanPham sp) {
        return repo.save(sp);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    @Override
    public List<SanPham> getAllByName(String name){
        return repo.findByTenSanPhamContainingIgnoreCase(name);
    }
}
