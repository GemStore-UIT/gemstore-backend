package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.NhaCungCap;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NhaCungCapService {

    List<NhaCungCap> getAll();

    Optional<NhaCungCap> getById(UUID id);

    NhaCungCap save(NhaCungCap ncc);

    void delete(UUID id);

    List<NhaCungCap> getAllByName(String name);
}