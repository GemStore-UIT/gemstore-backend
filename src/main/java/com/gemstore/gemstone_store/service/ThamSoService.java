package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.model.ThamSo;

import java.util.List;
import java.util.Optional;

public interface ThamSoService {

    List<ThamSo> getAll();
    Optional<ThamSo> getByName(String name);
    ThamSo save(ThamSo ts);
    void delete(String id);

}
