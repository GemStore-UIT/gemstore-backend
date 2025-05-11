package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.model.PhieuMuaHang;
import com.gemstore.gemstone_store.service.PhieuDichVuService;
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
@RequestMapping("/api/phieudichvu")
@Tag(name = "Phiếu dịch vụ", description = "CRUD dịch vụ")
public class PhieuDichVuController {

    @Autowired
    private PhieuDichVuService service;

    @Operation(summary = "Lấy tất cả phiếu dịch vụ")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<PhieuDichVu> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có phiếu dịch vụ nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy phiếu dịch vụ theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<PhieuDichVu> pdv = service.getById(id);
        return pdv.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy phiếu dịch vụ với mã: " + id));
    }

    @Operation(summary = "Cập nhật phiếu dịch vụ")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody PhieuDichVu pdv, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        PhieuDichVu saved = service.save(pdv);
        return ResponseEntity.status(
                pdv.getSoPhieuDV() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa phiếu dịch vụ")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<PhieuDichVu> pdv = service.getById(id);
        if (pdv.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phiếu dịch vụ để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa phiếu dịch vụ thành công.");
    }
}
