package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.DonViTinh;
import com.gemstore.gemstone_store.repository.DonViTinhRepository;
import com.gemstore.gemstone_store.service.DonViTinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonViTinhServiceImpl implements DonViTinhService {

    @Autowired
    private DonViTinhRepository repo;

    @Override
    public List<DonViTinh> getAll(){
        return repo.findAll();
    }

    @Override
    public Optional<DonViTinh> getById(String id){
        return repo.findById(id);
    }

    @Override
    public DonViTinh save(DonViTinh dvt){
        return repo.save(dvt);
    }

    @Override
    public void delete(String id){
        repo.deleteById(id);
    }
}
