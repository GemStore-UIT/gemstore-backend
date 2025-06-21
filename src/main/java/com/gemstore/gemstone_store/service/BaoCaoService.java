package com.gemstore.gemstone_store.service;

import com.gemstore.gemstone_store.dto.response.BaoCaoResponse;

import java.util.List;

public interface BaoCaoService {
    List<BaoCaoResponse> getBaoCao(int thang, int nam);
}
