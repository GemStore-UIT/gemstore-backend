package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.DonViTinh;

import java.util.List;
import java.util.Optional;

public interface DonViTinhService {

    List<DonViTinh> getAll();
    Optional<DonViTinh> getById(String id);
    DonViTinh save(DonViTinh dvt);
    void delete(String id);

    List<DonViTinh> getAllByName(String name);
}
