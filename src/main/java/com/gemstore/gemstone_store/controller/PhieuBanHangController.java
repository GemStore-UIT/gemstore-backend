package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.dto.request.PhieuBanHangRequest;
import com.gemstore.gemstone_store.dto.response.PhieuBanHangResponse;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
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
@RequestMapping("/api/phieubanhang")
@Tag(name = "Phiếu bán hàng", description = "CRUD phiếu bán hàng")
public class PhieuBanHangController {

    @Autowired
    private PhieuBanHangService service;

    @Operation(summary = "Lấy tất cả phiếu bán hàng")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/phieubanhang - Lấy tất cả phiếu bán hàng");
        List<PhieuBanHangResponse> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có phiếu bán hàng nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu bán hàng nào.");
        }
        log.debug("Trả về {} phiếu bán hàng", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy phiếu bán hàng theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        log.info("API GET /api/phieubanhang/{} - Tìm phiếu bán hàng", id);
        Optional<PhieuBanHangResponse> pbh = service.getById(id);
        return pbh.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy phiếu bán hàng với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy phiếu bán hàng với mã: " + id);
                });
    }

    @PostMapping
    @Operation(summary = "Tạo hoặc cập nhật phiếu bán hàng")
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody PhieuBanHang pbh) {
        log.info("API POST /api/phieubanhang - Tạo/cập nhật phiếu bán hàng: {}", pbh);
        PhieuBanHang saved = service.save(pbh);
        log.info("Đã lưu phiếu bán hàng thành công với id={}", saved.getSoPhieuBH());
        return ResponseEntity.status(
                pbh.getSoPhieuBH() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @PostMapping("/full")
    @Operation(summary = "Tạo phiếu bán hàng cùng chi tiết")
    public ResponseEntity<?> createWithCT(@Valid @RequestBody PhieuBanHangRequest req) {
        PhieuBanHangResponse saved = service.saveWithCT(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa phiếu bán hàng theo mã")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        log.info("API DELETE /api/phieubanhang/{} - Xóa phiếu bán hàng", id);
        Optional<PhieuBanHangResponse> pbh = service.getById(id);
        if (pbh.isEmpty()) {
            log.warn("Không tìm thấy phiếu bán hàng với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu bán hàng để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa phiếu bán hàng thành công với id={}", id);
        return ResponseEntity.ok("Xóa phiếu bán hàng thành công.");
    }
}
