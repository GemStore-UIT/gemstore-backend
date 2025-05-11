package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phieudichvu")
public class PhieuDichVuController {

    @Autowired
    private PhieuDichVuService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<PhieuDichVu> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu dịch vụ nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<PhieuDichVu> pdv = service.getById(id);
        return pdv.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy phiếu dịch vụ với mã: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody PhieuDichVu pdv) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pdv));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<PhieuDichVu> pdv = service.getById(id);
        if (pdv.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu dịch vụ để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa phiếu dịch vụ thành công.");
    }
}
