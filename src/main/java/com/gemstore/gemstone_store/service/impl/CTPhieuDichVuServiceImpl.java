package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import com.gemstore.gemstone_store.repository.CTPhieuDichVuRepository;
import com.gemstore.gemstone_store.service.CTPhieuDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CTPhieuDichVuServiceImpl implements CTPhieuDichVuService {

    @Autowired
    private CTPhieuDichVuRepository repo;

    @Override
    public List<CTPhieuDichVu> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<CTPhieuDichVu> getById(CTPhieuDichVuId id) {
        return repo.findById(id);
    }

    @Override
    public CTPhieuDichVu save(CTPhieuDichVu ct) {
        return repo.save(ct);
    }

    @Override
    public void delete(CTPhieuDichVuId id) {
        repo.deleteById(id);
    }
}
