package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.ThamSo;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
import com.gemstore.gemstone_store.service.ThamSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/thamso")
public class ThamSoController {

    @Autowired
    private ThamSoService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ThamSo> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có tham số nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getById(@PathVariable String name) {
        Optional<ThamSo> pbh = service.getByName(name);
        return pbh.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy phiếu bán hàng với tên: " + name));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody ThamSo ts) {
        ThamSo saved = service.save(ts);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        Optional<ThamSo> pbh = service.getByName(name);
        if (pbh.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy tham số để xóa.");
        }
        service.delete(name);
        return ResponseEntity.ok("Xóa tham số thành công.");
    }
}
