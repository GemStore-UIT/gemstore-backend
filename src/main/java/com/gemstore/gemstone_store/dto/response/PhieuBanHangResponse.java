package com.gemstore.gemstone_store.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PhieuBanHangResponse {
    private UUID soPhieuBH;
    private LocalDateTime ngayLap;
    private String khachHang;
    private Integer tongTien;
    private List<CTPhieuBanHangResponse> chiTiet;
}
