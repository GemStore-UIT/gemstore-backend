package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.service.PhieuMuaHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/phieumuahang")
@Tag(name = "Phiếu mua hàng", description = "CRUD phiếu mua hàng")
public class PhieuMuaHangController {

    @Autowired
    private PhieuMuaHangService service;

    @Operation(summary = "Lấy tất cả phiếu mua hàng")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<PhieuMuaHang> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu mua hàng nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy phiếu mua hàng theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<PhieuMuaHang> pmh = service.getById(id);
        return pmh.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy phiếu mua hàng với mã: " + id));
    }

    @Operation(summary = "Tạo hoặc cập nhật phiếu mua hàng")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody PhieuMuaHang pmh, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        PhieuMuaHang saved = service.save(pmh);
        return ResponseEntity.status(
                pmh.getSoPhieuMH() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa phiếu mua hàng theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam String id) {
        Optional<PhieuMuaHang> pmh = service.getById(id);
        if (pmh.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu mua hàng để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa phiếu mua hàng thành công.");
    }
}
