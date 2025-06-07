package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.service.LoaiDichVuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/loaidichvu")
@Tag(name = "Loại dịch vụ", description = "CRUD loại dịch vụ")
public class LoaiDichVuController {

    @Autowired
    private LoaiDichVuService service;

    @Operation(summary = "Lấy tất cả loại dịch vụ")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/loaidichvu - Lấy tất cả loại dịch vụ");
        List<LoaiDichVu> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có loại dịch vụ nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có loại dịch vụ nào.");
        }
        log.debug("Trả về {} loại dịch vụ", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy loại dịch vụ theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        log.info("API GET /api/loaidichvu/{} - Tìm loại dịch vụ", id);
        Optional<LoaiDichVu> ldv = service.getById(id);
        return ldv.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy loại dịch vụ với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy loại dịch vụ với mã: " + id);
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật loại dịch vụ")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody LoaiDichVu ldv) {
        log.info("API POST /api/loaidichvu - Tạo hoặc cập nhật loại dịch vụ: {}", ldv);
        LoaiDichVu saved = service.save(ldv);
        log.info("Đã lưu loại dịch vụ thành công với id={}", saved.getMaLDV());
        return ResponseEntity.status(
                ldv.getMaLDV() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa loại dịch vụ")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        log.info("API DELETE /api/loaidichvu/{} - Xóa loại dịch vụ", id);
        Optional<LoaiDichVu> ldv = service.getById(id);
        if (ldv.isEmpty()) {
            log.warn("Không tìm thấy loại dịch vụ với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy loại dịch vụ để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa loại dịch vụ thành công với id={}", id);
        return ResponseEntity.ok("Xóa loại dịch vụ thành công.");
    }
}
