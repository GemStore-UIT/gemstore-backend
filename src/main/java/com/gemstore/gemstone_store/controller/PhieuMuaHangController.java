package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.dto.request.PhieuBanHangRequest;
import com.gemstore.gemstone_store.dto.request.PhieuMuaHangRequest;
import com.gemstore.gemstone_store.dto.response.PhieuMuaHangResponse;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.service.PhieuMuaHangService;
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
@RequestMapping("/api/phieumuahang")
@Tag(name = "Phiếu mua hàng", description = "CRUD phiếu mua hàng")
public class PhieuMuaHangController {

    @Autowired
    private PhieuMuaHangService service;

    @Operation(summary = "Lấy tất cả phiếu mua hàng")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/phieumuahang - Lấy tất cả phiếu mua hàng");
        List<PhieuMuaHangResponse> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có phiếu mua hàng nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu mua hàng nào.");
        }
        log.debug("Trả về {} phiếu mua hàng", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy phiếu mua hàng theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        log.info("API GET /api/phieumuahang/{} - Tìm phiếu mua hàng", id);
        Optional<PhieuMuaHangResponse> pmh = service.getById(id);
        return pmh.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy phiếu mua hàng với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy phiếu mua hàng với mã: " + id);
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật phiếu mua hàng")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody PhieuMuaHang pmh) {
        log.info("API POST /api/phieumuahang - Tạo/cập nhật phiếu mua hàng: {}", pmh);
        PhieuMuaHang saved = service.save(pmh);
        log.info("Đã lưu phiếu mua hàng thành công với id={}", saved.getSoPhieuMH());
        return ResponseEntity.status(
                pmh.getSoPhieuMH() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @PostMapping("/full")
    @Operation(summary = "Tạo phiếu bán hàng cùng chi tiết")
    public ResponseEntity<?> createWithCT(@Valid @RequestBody PhieuMuaHangRequest req) {
        PhieuMuaHangResponse saved = service.saveWithCT(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Xóa phiếu mua hàng theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        log.info("API DELETE /api/phieumuahang/{} - Xóa phiếu mua hàng", id);
        Optional<PhieuMuaHangResponse> pmh = service.getById(id);
        if (pmh.isEmpty()) {
            log.warn("Không tìm thấy phiếu mua hàng với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu mua hàng để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa phiếu mua hàng thành công với id={}", id);
        return ResponseEntity.ok("Xóa phiếu mua hàng thành công.");
    }
}
