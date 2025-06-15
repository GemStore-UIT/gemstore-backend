package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.NhaCungCap;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.service.SanPhamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/sanpham")
@Tag(name = "Sản phẩm", description = "CRUD sản phẩm")
public class SanPhamController {

    @Autowired
    private SanPhamService service;

    @Operation(summary = "Lấy tất cả sản phẩm")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/sanpham - Lấy tất cả sản phẩm");
        List<SanPham> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có sản phẩm nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có sản phẩm nào.");
        }
        log.debug("Trả về {} sản phẩm", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy sản phẩm theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        log.info("API GET /api/sanpham/{} - Tìm sản phẩm", id);
        Optional<SanPham> sp = service.getById(id);
        return sp.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy sản phẩm với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy sản phẩm với mã: " + id);
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật sản phẩm")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody SanPham sp) {
        log.info("API POST /api/sanpham - Tạo hoặc cập nhật sản phẩm: {}", sp);
        SanPham saved = service.save(sp);
        log.info("Đã lưu sản phẩm thành công với id={}", saved.getMaSanPham());
        return ResponseEntity.status(
                sp.getMaSanPham() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa sản phẩm theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        log.info("API DELETE /api/sanpham/{} - Xóa sản phẩm", id);
        Optional<SanPham> sp = service.getById(id);
        if (sp.isEmpty()) {
            log.warn("Không tìm thấy sản phẩm với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy sản phẩm để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa sản phẩm thành công với id={}", id);
        return ResponseEntity.ok("Xóa sản phẩm thành công.");
    }

    @Operation(summary = "Tìm sản phẩm theo tên")
    @GetMapping("/search")
    public ResponseEntity<?> searchByTen(@RequestParam String keyword) {
        List<SanPham> result = service.getAllByName(keyword);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả nào.");
        }
        return ResponseEntity.ok(result);
    }
}
