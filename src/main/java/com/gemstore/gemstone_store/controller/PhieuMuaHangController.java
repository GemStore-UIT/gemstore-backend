package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.service.PhieuMuaHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phieumuahang")
public class PhieuMuaHangController {

    @Autowired
    private PhieuMuaHangService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<PhieuMuaHang> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu mua hàng nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<PhieuMuaHang> pmh = service.getById(id);
        return pmh.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy phiếu mua hàng với mã: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody PhieuMuaHang pmh) {
        PhieuMuaHang saved = service.save(pmh);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam String id) {
        Optional<PhieuMuaHang> pmh = service.getById(id);
        if (pmh.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu mua hàng để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa phiếu mua hàng thành công.");
    }
}
