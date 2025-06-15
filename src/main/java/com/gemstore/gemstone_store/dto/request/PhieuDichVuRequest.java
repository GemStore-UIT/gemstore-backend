package com.gemstore.gemstone_store.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PhieuDichVuRequest {
    private String khachHang;
    private String sdt;
    private List<CTPhieuDichVuRequest> chiTiet;
}
