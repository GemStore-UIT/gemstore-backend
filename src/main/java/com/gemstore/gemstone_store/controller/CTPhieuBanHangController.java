package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import com.gemstore.gemstone_store.repository.PhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.CTPhieuBanHangService;
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
@RequestMapping("/api/ctphieubanhang")
@Tag(name = "Chi tiết phiếu bán hàng", description = "CRUD chi tiết phiếu bán hàng")
public class CTPhieuBanHangController {

    @Autowired
    private CTPhieuBanHangService service;

    @Autowired
    private PhieuBanHangRepository phieuBanHangRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Operation(summary = "Lấy tất cả chi tiết phiếu bán hàng")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/ctphieubanhang - Lấy tất cả chi tiết phiếu bán hàng");
        List<CTPhieuBanHang> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có chi tiết phiếu bán hàng nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chi tiết phiếu bán hàng nào.");
        }
        log.debug("Trả về {} chi tiết phiếu bán hàng", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy chi tiết phiếu bán hàng theo mã")
    @GetMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> getById(@PathVariable String maSP, @PathVariable String soPhieu) {
        log.info("API GET /api/ctphieubanhang/{}/{} - Tìm chi tiết phiếu bán hàng", maSP, soPhieu);
        CTPhieuBanHangId id = new CTPhieuBanHangId(maSP, soPhieu);
        Optional<CTPhieuBanHang> ct = service.getById(id);
        return ct.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy chi tiết phiếu bán hàng với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy chi tiết phiếu bán hàng.");
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật chi tiết phiếu bán hàng")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody CTPhieuBanHang ct) {
        log.info("API POST /api/ctphieubanhang - Tạo/cập nhật chi tiết phiếu bán hàng: {}", ct);
        CTPhieuBanHang saved = service.save(ct);
        log.info("Đã lưu chi tiết phiếu bán hàng thành công với id={}", saved.getId());
        return ResponseEntity.status(
                ct.getId() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa chi tiết phiếu bán hàng theo mã")
    @DeleteMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> delete(@PathVariable String maSP, @PathVariable String soPhieu) {
        log.info("API DELETE /api/ctphieubanhang/{}/{} - Xóa chi tiết phiếu bán hàng", maSP, soPhieu);
        CTPhieuBanHangId id = new CTPhieuBanHangId(maSP, soPhieu);
        Optional<CTPhieuBanHang> ct = service.getById(id);
        if (ct.isEmpty()) {
            log.warn("Không tìm thấy chi tiết phiếu bán hàng với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa chi tiết phiếu bán hàng thành công với id={}", id);
        return ResponseEntity.ok("Xóa thành công.");
    }
}
