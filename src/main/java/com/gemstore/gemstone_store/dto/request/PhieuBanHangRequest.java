package com.gemstore.gemstone_store.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PhieuBanHangRequest {
    private String khachHang;
    private List<CTPhieuBanHangRequest> chiTiet;
}
