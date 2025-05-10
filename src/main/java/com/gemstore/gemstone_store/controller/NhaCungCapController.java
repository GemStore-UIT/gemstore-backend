package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.NhaCungCap;
import com.gemstore.gemstone_store.service.NhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nhacungcap")
public class NhaCungCapController {

    @Autowired
    private NhaCungCapService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<NhaCungCap> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có nhà cung cấp nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<NhaCungCap> ncc = service.getById(id);
        return ncc.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy nhà cung cấp với mã: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody NhaCungCap ncc) {
        NhaCungCap saved = service.save(ncc);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam String id) {
        Optional<NhaCungCap> ncc = service.getById(id);
        if (ncc.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy nhà cung cấp để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa nhà cung cấp thành công.");
    }
}
