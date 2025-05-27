package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.repository.LoaiDichVuRepository;
import com.gemstore.gemstone_store.service.LoaiDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiDichVuServiceImpl implements LoaiDichVuService {

    @Autowired
    private LoaiDichVuRepository repo;

    @Override
    public List<LoaiDichVu> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<LoaiDichVu> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public LoaiDichVu save(LoaiDichVu ldv) {
        return repo.save(ldv);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    @Override
    public List<LoaiDichVu> getAllByName(String name){
        return repo.findByTenDichVuContainingIgnoreCase(name);
    }
}
