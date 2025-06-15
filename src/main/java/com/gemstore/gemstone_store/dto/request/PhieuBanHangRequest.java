package com.gemstore.gemstone_store.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PhieuBanHangRequest {
    private String khachHang;
    private String sdt;
    private List<CTPhieuBanHangRequest> chiTiet;
}

@Data
class CTPhieuBanHangRequest {
    private String maSanPham;
    private Integer soLuong;
    // Có thể thêm các trường như donGia, thanhTien nếu muốn nhập từ ngoài vào
}