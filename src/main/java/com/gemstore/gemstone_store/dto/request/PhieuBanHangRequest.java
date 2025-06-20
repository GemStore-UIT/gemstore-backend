package com.gemstore.gemstone_store.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PhieuBanHangRequest {
    private UUID soPhieuBH;
    private String khachHang;
    private List<CTPhieuBanHangRequest> chiTiet;
}
