package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phieubanhang")
public class PhieuBanHangController {

    @Autowired
    private PhieuBanHangService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<PhieuBanHang> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu bán hàng nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<PhieuBanHang> pbh = service.getById(id);
        return pbh.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy phiếu bán hàng với mã: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody PhieuBanHang pbh) {
        PhieuBanHang saved = service.save(pbh);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<PhieuBanHang> pbh = service.getById(id);
        if (pbh.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu bán hàng để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa phiếu bán hàng thành công.");
    }
}
