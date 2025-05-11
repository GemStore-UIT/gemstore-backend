package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
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
@RequestMapping("/api/phieubanhang")
@Tag(name = "Phiếu bán hàng", description = "CRUD phiếu bán hàng")
public class PhieuBanHangController {

    @Autowired
    private PhieuBanHangService service;

    @Operation(summary = "Lấy tất cả phiếu bán hàng")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<PhieuBanHang> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu bán hàng nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy phiếu bán hàng theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<PhieuBanHang> pbh = service.getById(id);
        return pbh.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy phiếu bán hàng với mã: " + id));
    }

    @PostMapping
    @Operation(summary = "Tạo hoặc cập nhật phiếu bán hàng")
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody PhieuBanHang pbh, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        PhieuBanHang saved = service.save(pbh);
        return ResponseEntity.status(
                pbh.getSoPhieuBH() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa phiếu bán hàng theo mã")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<PhieuBanHang> pbh = service.getById(id);
        if (pbh.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu bán hàng để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa phiếu bán hàng thành công.");
    }
}
