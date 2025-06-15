package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.DonViTinh;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonViTinhService {

    List<DonViTinh> getAll();
    Optional<DonViTinh> getById(UUID id);
    DonViTinh save(DonViTinh dvt);
    void delete(UUID id);

    List<DonViTinh> getAllByName(String name);
}
