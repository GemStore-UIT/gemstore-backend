package com.gemstore.gemstone_store.dto.response;

import lombok.Data;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class PhieuMuaHangResponse {
    private UUID soPhieuMH;
    private LocalDateTime ngayLap;
    private String tenNhaCungCap;
    private Integer tongTien;
    private List<CTPhieuMuaHangResponse> chiTiet;
}
