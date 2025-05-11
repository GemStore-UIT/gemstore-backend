package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import com.gemstore.gemstone_store.service.CTPhieuMuaHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ctphieumuahang")
public class CTPhieuMuaHangController {

    @Autowired
    private CTPhieuMuaHangService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CTPhieuMuaHang> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chi tiết phiếu mua hàng nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> getById(@PathVariable String maSP, @PathVariable String soPhieu) {
        CTPhieuMuaHangId id = new CTPhieuMuaHangId(maSP, soPhieu);
        Optional<CTPhieuMuaHang> ct = service.getById(id);
        return ct.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy chi tiết phiếu mua hàng."));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody CTPhieuMuaHang ct) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(ct));
    }

    @DeleteMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> delete(@PathVariable String maSP, @PathVariable String soPhieu) {
        CTPhieuMuaHangId id = new CTPhieuMuaHangId(maSP, soPhieu);
        Optional<CTPhieuMuaHang> ct = service.getById(id);
        if (ct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa thành công.");
    }
}
