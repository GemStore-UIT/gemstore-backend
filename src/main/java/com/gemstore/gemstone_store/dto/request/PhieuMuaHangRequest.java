package com.gemstore.gemstone_store.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PhieuMuaHangRequest {
    private UUID soPhieuMH;
    private UUID nhaCungCap;
    private List<CTPhieuMuaHangRequest> chiTiet;
}
