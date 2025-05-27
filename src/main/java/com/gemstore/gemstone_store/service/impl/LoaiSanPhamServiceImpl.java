package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.LoaiSanPham;
import com.gemstore.gemstone_store.repository.LoaiSanPhamRepository;
import com.gemstore.gemstone_store.service.LoaiSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {

    @Autowired
    private LoaiSanPhamRepository repo;

    @Override
    public List<LoaiSanPham> getAll(){ return repo.findAll(); }

    @Override
    public Optional<LoaiSanPham> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public LoaiSanPham save(LoaiSanPham loaiSanPham){
        return repo.save(loaiSanPham);
    }

    @Override
    public void delete(String id){
        repo.deleteById(id);
    }

    @Override
    public List<LoaiSanPham> getAllByName(String name){
        return repo.findByTenLSPContainingIgnoreCase(name);
    }
}
