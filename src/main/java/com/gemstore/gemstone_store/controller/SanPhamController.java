package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.NhaCungCap;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.service.SanPhamService;
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
@RequestMapping("/api/sanpham")
@Tag(name = "Sản phẩm", description = "CRUD sản phẩm")
public class SanPhamController {

    @Autowired
    private SanPhamService service;

    @Operation(summary = "Lấy tất cả sản phẩm")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<SanPham> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có sản phẩm nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy sản phẩm theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<SanPham> sp = service.getById(id);
        return sp.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy sản phẩm với mã: " + id));
    }

    @Operation(summary = "Tạo hoặc cập nhật sản phẩm")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody SanPham sp, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        SanPham saved = service.save(sp);
        return ResponseEntity.status(
                sp.getMaSanPham() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa sản phẩm theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam String id) {
        Optional<SanPham> sp = service.getById(id);
        if (sp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy sản phẩm để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công.");
    }

    @Operation(summary = "Tìm sản phẩm theo tên")
    @GetMapping("/search")
    public ResponseEntity<?> searchByTen(@RequestParam String keyword) {
        List<SanPham> result = service.getAllByName(keyword);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả nào.");
        }
        return ResponseEntity.ok(result);
    }
}
