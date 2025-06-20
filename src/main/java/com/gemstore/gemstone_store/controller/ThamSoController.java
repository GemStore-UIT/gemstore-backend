package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.ThamSo;
import com.gemstore.gemstone_store.service.ThamSoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/thamso")
@Tag(name = "Tham số", description = "CRUD tham số")
public class ThamSoController {

    @Autowired
    private ThamSoService service;

    @Operation(summary = "Lấy tất cả tham số")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/thamso - Lấy tất cả tham số");
        List<ThamSo> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có tham số nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có tham số nào.");
        }
        log.debug("Trả về {} tham số", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy tham số theo mã")
    @GetMapping("/{name}")
    public ResponseEntity<?> getById(@PathVariable String name) {
        log.info("API GET /api/thamso/{} - Tìm tham số", name);
        Optional<ThamSo> ts = service.getByName(name);
        return ts.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy tham số với tên={}", name);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy tham số với tên: " + name);
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật tham số")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody ThamSo ts) {
        log.info("API POST /api/thamso - Tạo hoặc cập nhật tham số: {}", ts);
        ThamSo saved = service.save(ts);
        log.info("Đã lưu tham số thành công với tên={}", saved.getTenThamSo());
        return ResponseEntity.status(
                ts.getTenThamSo() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xoá tham số theo mã")
    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        log.info("API DELETE /api/thamso/{} - Xóa tham số", name);
        Optional<ThamSo> ts = service.getByName(name);
        if (ts.isEmpty()) {
            log.warn("Không tìm thấy tham số với tên={} để xóa.", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy tham số để xóa.");
        }
        service.delete(name);
        log.info("Đã xóa tham số thành công với tên={}", name);
        return ResponseEntity.ok("Xóa tham số thành công.");
    }
}
