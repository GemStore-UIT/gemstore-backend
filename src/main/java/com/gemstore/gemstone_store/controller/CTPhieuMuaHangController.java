package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import com.gemstore.gemstone_store.repository.PhieuMuaHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.CTPhieuMuaHangService;
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
@RequestMapping("/api/ctphieumuahang")
@Tag(name = "Chi tiết phiếu mua hàng", description = "CRUD chi tiết phiếu mua hàng")
public class CTPhieuMuaHangController {

    @Autowired
    private CTPhieuMuaHangService service;

    @Autowired
    private PhieuMuaHangRepository phieuMuaHangRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Operation(summary = "Lấy tất cả chi tiết phiếu mua hàng")
    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info("API GET /api/ctphieumuahang - Lấy tất cả chi tiết phiếu mua hàng");
        List<CTPhieuMuaHang> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có chi tiết phiếu mua hàng nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chi tiết phiếu mua hàng nào.");
        }
        log.debug("Trả về {} chi tiết phiếu mua hàng", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy chi tiết phiếu mua hàng theo mã")
    @GetMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> getById(@PathVariable String maSP, @PathVariable String soPhieu) {
        log.info("API GET /api/ctphieumuahang/{}/{} - Tìm chi tiết phiếu mua hàng", maSP, soPhieu);
        CTPhieuMuaHangId id = new CTPhieuMuaHangId(maSP, soPhieu);
        Optional<CTPhieuMuaHang> ct = service.getById(id);
        return ct.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy chi tiết phiếu mua hàng với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy chi tiết phiếu mua hàng.");
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật chi tiết phiếu mua hàng")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody CTPhieuMuaHang ct) {
        log.info("API POST /api/ctphieumuahang - Tạo/cập nhật chi tiết phiếu mua hàng: {}", ct);
        CTPhieuMuaHang saved = service.save(ct);
        log.info("Đã lưu chi tiết phiếu mua hàng thành công với id={}", saved.getId());
        return ResponseEntity.status(
                ct.getId() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa chi tiết phiếu mua hàng theo mã")
    @DeleteMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> delete(@PathVariable String maSP, @PathVariable String soPhieu) {
        log.info("API DELETE /api/ctphieumuahang/{}/{} - Xóa chi tiết phiếu mua hàng", maSP, soPhieu);
        CTPhieuMuaHangId id = new CTPhieuMuaHangId(maSP, soPhieu);
        Optional<CTPhieuMuaHang> ct = service.getById(id);
        if (ct.isEmpty()) {
            log.warn("Không tìm thấy chi tiết phiếu mua hàng với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa chi tiết phiếu mua hàng thành công với id={}", id);
        return ResponseEntity.ok("Xóa thành công.");
    }
}
