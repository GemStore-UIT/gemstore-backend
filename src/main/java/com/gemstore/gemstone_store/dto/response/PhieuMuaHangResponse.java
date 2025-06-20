package com.gemstore.gemstone_store.dto.response;

import com.gemstore.gemstone_store.model.NhaCungCap;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PhieuMuaHangResponse {
    private UUID soPhieuMH;
    private LocalDateTime ngayLap;
    private NhaCungCap nhaCungCap;
    private Integer tongTien;
    private List<CTPhieuMuaHangResponse> chiTiet;
}
