package com.gemstore.gemstone_store.dto.response;

import lombok.Data;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class PhieuDichVuResponse {
    private UUID soPhieuDV;
    private LocalDateTime ngayLap;
    private String khachHang;
    private String sdt;
    private int tongTien;
    private int tongTienTraTruoc;
    private int tongTienConLai;
    private String tinhTrang;
    private List<CTPhieuDichVuResponse> chiTiet;
}
