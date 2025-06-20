package com.gemstore.gemstone_store.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PhieuDichVuRequest {
    private UUID soPhieuDV;
    private String khachHang;
    private String sdt;
    private List<CTPhieuDichVuRequest> chiTiet;
}
