package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.CTPhieuMuaHang;
import com.gemstore.gemstone_store.model.id.CTPhieuMuaHangId;
import com.gemstore.gemstone_store.service.CTPhieuMuaHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ctphieumuahang")
@Tag(name = "Chi tiết phiếu mua hàng", description = "CRUD chi tiết phiếu mua hàng")
public class CTPhieuMuaHangController {

    @Autowired
    private CTPhieuMuaHangService service;

    @Operation(summary = "Lấy tất cả chi tiết phiếu mua hàng")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CTPhieuMuaHang> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chi tiết phiếu mua hàng nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy chi tiết phiếu mua hàng theo mã")
    @GetMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> getById(@PathVariable String maSP, @PathVariable String soPhieu) {
        CTPhieuMuaHangId id = new CTPhieuMuaHangId(maSP, soPhieu);
        Optional<CTPhieuMuaHang> ct = service.getById(id);
        return ct.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy chi tiết phiếu mua hàng."));
    }

    @Operation(summary = "Tạo hoặc cập nhật chi tiết phiếu mua hàng")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody CTPhieuMuaHang ct, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        CTPhieuMuaHang saved = service.save(ct);
        return ResponseEntity.status(
                ct.getId() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa chi tiết phiếu mua hàng theo mã")
    @DeleteMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> delete(@PathVariable String maSP, @PathVariable String soPhieu) {
        CTPhieuMuaHangId id = new CTPhieuMuaHangId(maSP, soPhieu);
        Optional<CTPhieuMuaHang> ct = service.getById(id);
        if (ct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa thành công.");
    }
}
