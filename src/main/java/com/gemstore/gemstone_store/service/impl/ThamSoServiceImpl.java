package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.ThamSo;
import com.gemstore.gemstone_store.repository.ThamSoRepository;
import com.gemstore.gemstone_store.service.ThamSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThamSoServiceImpl implements ThamSoService {

    @Autowired
    private ThamSoRepository repo;

    @Override
    public List<ThamSo> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<ThamSo> getByName(String name) {
        return repo.findById(name);
    }

    @Override
    public ThamSo save(ThamSo ts) {
        return repo.save(ts);
    }

    @Override
    public void delete(String name) {
        repo.deleteById(name);
    }

}
