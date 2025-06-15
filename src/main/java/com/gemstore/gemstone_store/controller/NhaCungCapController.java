package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.NhaCungCap;
import com.gemstore.gemstone_store.service.NhaCungCapService;
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
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/nhacungcap")
@Tag(name = "Nhà cung cấp", description = "CRUD nhà cung cấp")
public class NhaCungCapController {

    @Autowired
    private NhaCungCapService service;

    @Operation(summary = "Lấy tất cả nhà cung cấp")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/nhacungcap - Lấy tất cả nhà cung cấp");
        List<NhaCungCap> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có nhà cung cấp nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có nhà cung cấp nào.");
        }
        log.debug("Trả về {} nhà cung cấp", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy nhà cung cấp theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        log.info("API GET /api/nhacungcap/{} - Tìm nhà cung cấp", id);
        Optional<NhaCungCap> ncc = service.getById(id);
        return ncc.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy nhà cung cấp với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy nhà cung cấp với mã: " + id);
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật nhà cung cấp")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody NhaCungCap ncc) {
        log.info("API POST /api/nhacungcap - Tạo hoặc cập nhật nhà cung cấp: {}", ncc);
        NhaCungCap saved = service.save(ncc);
        log.info("Đã lưu nhà cung cấp thành công với id={}", saved.getMaNCC());
        return ResponseEntity.status(
                ncc.getMaNCC() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa nhà cung cấp theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        log.info("API DELETE /api/nhacungcap/{} - Xóa nhà cung cấp", id);
        Optional<NhaCungCap> ncc = service.getById(id);
        if (ncc.isEmpty()) {
            log.warn("Không tìm thấy nhà cung cấp với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy nhà cung cấp để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa nhà cung cấp thành công với id={}", id);
        return ResponseEntity.ok("Xóa nhà cung cấp thành công.");
    }

    @Operation(summary = "Tìm nhà cung cấp theo tên")
    @GetMapping("/search")
    public ResponseEntity<?> searchByTen(@RequestParam String keyword) {
        List<NhaCungCap> result = service.getAllByName(keyword);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả nào.");
        }
        return ResponseEntity.ok(result);
    }
}
