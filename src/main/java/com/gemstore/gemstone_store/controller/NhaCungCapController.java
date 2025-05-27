package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.LoaiSanPham;
import com.gemstore.gemstone_store.model.NhaCungCap;
import com.gemstore.gemstone_store.service.NhaCungCapService;
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
@RequestMapping("/api/nhacungcap")
@Tag(name = "Nhà cung cấp", description = "CRUD nhà cung cấp")
public class NhaCungCapController {

    @Autowired
    private NhaCungCapService service;

    @Operation(summary = "Lấy tất cả nhà cung cấp")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<NhaCungCap> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có nhà cung cấp nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy nhà cung cấp theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<NhaCungCap> ncc = service.getById(id);
        return ncc.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy nhà cung cấp với mã: " + id));
    }

    @Operation(summary = "Tạo hoặc cập nhật nhà cung cấp")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody NhaCungCap ncc, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        NhaCungCap saved = service.save(ncc);
        return ResponseEntity.status(
                ncc.getMaNCC() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa nhà cung cấp theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam String id) {
        Optional<NhaCungCap> ncc = service.getById(id);
        if (ncc.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy nhà cung cấp để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa nhà cung cấp thành công.");
    }

    @Operation(summary = "Tìm nhà cung cấp theo tên")
    @GetMapping("/search")
    public ResponseEntity<?> searchByTen(@RequestParam String keyword) {
        List<NhaCungCap> result = service.getAllByName(keyword);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả nào.");
        }
        return ResponseEntity.ok(result);
    }
}
