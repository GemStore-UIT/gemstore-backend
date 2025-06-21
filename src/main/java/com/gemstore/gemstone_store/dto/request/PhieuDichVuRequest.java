package com.gemstore.gemstone_store.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PhieuDichVuRequest {
    private UUID soPhieuDV;
    private String khachHang;

    @Pattern(regexp = "^\\d{10,15}", message = "Số điện thoại phải là chữ số, từ 10 đến 15 số")
    private String sdt;
    private List<CTPhieuDichVuRequest> chiTiet;
}
