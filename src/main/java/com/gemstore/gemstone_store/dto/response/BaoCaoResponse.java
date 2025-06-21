package com.gemstore.gemstone_store.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class BaoCaoResponse {
    private UUID maSanPham;
    private String tenSanPham;
    private Integer tonDau;
    private Integer muaVao;
    private Integer banRa;
    private Integer tonCuoi;
    private String donViTinh;
}
