package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.model.LoaiSanPham;
import com.gemstore.gemstone_store.service.LoaiSanPhamService;
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
@RequestMapping("/api/loaisanpham")
@Tag(name = "Loại sản phẩm", description = "CRUD loại sản phẩm")
public class LoaiSanPhamController {

    @Autowired
    private LoaiSanPhamService service;

    @Operation(summary = "Lấy tất cả loại sản phẩm")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/loaisanpham - Lấy tất cả loại sản phẩm");
        List<LoaiSanPham> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có loại sản phẩm nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có loại sản phẩm nào.");
        }
        log.debug("Trả về {} loại sản phẩm", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy loại sản phẩm theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        log.info("API GET /api/loaisanpham/{} - Tìm loại sản phẩm", id);
        Optional<LoaiSanPham> lsp = service.getById(id);
        return lsp.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy loại sản phẩm với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy loại sản phẩm với mã: " + id);
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật loại sản phẩm")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody LoaiSanPham lsp) {
        log.info("API POST /api/loaisanpham - Tạo hoặc cập nhật loại sản phẩm: {}", lsp);
        LoaiSanPham saved = service.save(lsp);
        log.info("Đã lưu loại sản phẩm thành công với id={}", saved.getMaLSP());
        return ResponseEntity.status(
                lsp.getMaLSP() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa loại sản phẩm theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        log.info("API DELETE /api/loaisanpham/{} - Xóa loại sản phẩm", id);
        Optional<LoaiSanPham> lsp = service.getById(id);
        if (lsp.isEmpty()) {
            log.warn("Không tìm thấy loại sản phẩm với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy loại sản phẩm để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa loại sản phẩm thành công với id={}", id);
        return ResponseEntity.ok("Xóa loại sản phẩm thành công.");
    }

    @Operation(summary = "Tìm loại sản phẩm theo tên")
    @GetMapping("/search")
    public ResponseEntity<?> searchByTen(@RequestParam String keyword) {
        List<LoaiSanPham> result = service.getAllByName(keyword);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả nào.");
        }
        return ResponseEntity.ok(result);
    }
}
