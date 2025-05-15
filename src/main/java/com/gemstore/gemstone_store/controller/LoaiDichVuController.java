package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.service.LoaiDichVuService;
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
@RequestMapping("/api/loaidichvu")
@Tag(name = "Loại dịch vụ", description = "CRUD loại dịch vụ")
public class LoaiDichVuController {

    @Autowired
    private LoaiDichVuService service;

    @Operation(summary = "Lấy tất cả loại dịch vụ")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<LoaiDichVu> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có loại dịch vụ nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy loại dịch vụ theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<LoaiDichVu> ldv = service.getById(id);
        return ldv.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy loại dịch vụ với mã: " + id));
    }

    @Operation(summary = "Tạo hoặc cập nhật loại dịch vụ")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody LoaiDichVu ldv, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        LoaiDichVu saved = service.save(ldv);
        return ResponseEntity.status(
                ldv.getMaLDV() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa loại dịch vụ")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<LoaiDichVu> ldv = service.getById(id);
        if (ldv.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy loại dịch vụ để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa loại dịch vụ thành công.");
    }
}
