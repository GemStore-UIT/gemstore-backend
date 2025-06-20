package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.dto.request.PhieuDichVuRequest;
import com.gemstore.gemstone_store.dto.response.PhieuDichVuResponse;
import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
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
@RequestMapping("/api/phieudichvu")
@Tag(name = "Phiếu dịch vụ", description = "CRUD dịch vụ")
public class PhieuDichVuController {

    @Autowired
    private PhieuDichVuService service;

    @Operation(summary = "Lấy tất cả phiếu dịch vụ")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/phieudichvu - Lấy tất cả phiếu dịch vụ");
        List<PhieuDichVuResponse> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có phiếu dịch vụ nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu dịch vụ nào.");
        }
        log.debug("Trả về {} phiếu dịch vụ", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy phiếu dịch vụ theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        log.info("API GET /api/phieudichvu/{} - Tìm phiếu dịch vụ", id);
        Optional<PhieuDichVuResponse> pdv = service.getById(id);
        return pdv.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy phiếu dịch vụ với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy phiếu dịch vụ với mã: " + id);
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật phiếu dịch vụ")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody PhieuDichVu pdv) {
        log.info("API POST /api/phieudichvu - Tạo/cập nhật phiếu dịch vụ: {}", pdv);
        PhieuDichVu saved = service.save(pdv);
        log.info("Đã lưu phiếu dịch vụ thành công với id={}", saved.getSoPhieuDV());
        return ResponseEntity.status(
                pdv.getSoPhieuDV() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @PostMapping("/full")
    @Operation(summary = "Tạo hoặc cập nhật phiếu dịch vụ cùng chi tiết")
    public ResponseEntity<?> createWithCT(@Valid @RequestBody PhieuDichVuRequest req) {
        PhieuDichVuResponse saved = service.saveWithCT(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Xóa phiếu dịch vụ")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        log.info("API DELETE /api/phieudichvu/{} - Xóa phiếu dịch vụ", id);
        Optional<PhieuDichVuResponse> pdv = service.getById(id);
        if (pdv.isEmpty()) {
            log.warn("Không tìm thấy phiếu dịch vụ với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu dịch vụ để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa phiếu dịch vụ thành công với id={}", id);
        return ResponseEntity.ok("Xóa phiếu dịch vụ thành công.");
    }
}
