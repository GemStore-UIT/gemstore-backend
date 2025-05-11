package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.repository.PhieuDichVuRepository;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhieuDichVuServiceImpl implements PhieuDichVuService {

    @Autowired
    private PhieuDichVuRepository repo;

    @Override
    public List<PhieuDichVu> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<PhieuDichVu> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public PhieuDichVu save(PhieuDichVu pdv) {
        return repo.save(pdv);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
