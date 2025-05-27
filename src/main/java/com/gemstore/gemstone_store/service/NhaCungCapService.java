package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.NhaCungCap;

import java.util.List;
import java.util.Optional;

public interface NhaCungCapService {

    List<NhaCungCap> getAll();

    Optional<NhaCungCap> getById(String id);

    NhaCungCap save(NhaCungCap ncc);

    void delete(String id);

    List<NhaCungCap> getAllByName(String name);
}