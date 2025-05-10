package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.NhaCungCap;
import com.gemstore.gemstone_store.repository.NhaCungCapRepository;
import com.gemstore.gemstone_store.service.NhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {

    @Autowired
    private NhaCungCapRepository repo;

    @Override
    public List<NhaCungCap> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<NhaCungCap> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public NhaCungCap save(NhaCungCap ncc) {
        return repo.save(ncc);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
