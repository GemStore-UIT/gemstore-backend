package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sanpham")
public class SanPhamController {

    @Autowired
    private SanPhamService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<SanPham> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có sản phẩm nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<SanPham> sp = service.getById(id);
        return sp.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy sản phẩm với mã: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody SanPham sp) {
        SanPham saved = service.save(sp);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam String id) {
        Optional<SanPham> sp = service.getById(id);
        if (sp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy sản phẩm để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công.");
    }
}
