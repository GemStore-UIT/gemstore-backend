package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.service.LoaiDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loaidichvu")
public class LoaiDichVuController {

    @Autowired
    private LoaiDichVuService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<LoaiDichVu> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có loại dịch vụ nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<LoaiDichVu> ldv = service.getById(id);
        return ldv.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy loại dịch vụ với mã: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody LoaiDichVu ldv) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(ldv));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<LoaiDichVu> ldv = service.getById(id);
        if (ldv.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy loại dịch vụ để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa loại dịch vụ thành công.");
    }
}
