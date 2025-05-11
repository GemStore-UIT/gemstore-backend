package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.ThamSo;
import com.gemstore.gemstone_store.service.PhieuBanHangService;
import com.gemstore.gemstone_store.service.ThamSoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
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
@RequestMapping("/api/thamso")
@Tag(name = "Tham số", description = "CRUD tham số")
public class ThamSoController {

    @Autowired
    private ThamSoService service;

    @Operation(summary = "Lấy tất cả tham số")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ThamSo> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có tham số nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy tham số theo mã")
    @GetMapping("/{name}")
    public ResponseEntity<?> getById(@PathVariable String name) {
        Optional<ThamSo> pbh = service.getByName(name);
        return pbh.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy phiếu bán hàng với tên: " + name));
    }

    @Operation(summary = "Tạo hoặc cập nhật tham số")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody ThamSo ts, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        ThamSo saved = service.save(ts);
        return ResponseEntity.status(
                ts.getTenThamSo() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xoá tham số theo mã")
    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@RequestParam String name) {
        Optional<ThamSo> pbh = service.getByName(name);
        if (pbh.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy tham số để xóa.");
        }
        service.delete(name);
        return ResponseEntity.ok("Xóa tham số thành công.");
    }
}
