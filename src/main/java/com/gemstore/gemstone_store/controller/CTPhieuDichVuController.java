package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.model.PhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import com.gemstore.gemstone_store.repository.LoaiDichVuRepository;
import com.gemstore.gemstone_store.repository.PhieuDichVuRepository;
import com.gemstore.gemstone_store.service.CTPhieuDichVuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ctphieudichvu")
@Tag(name = "Chi tiết phiếu dịch vụ", description = "CRUD chi tiết phiếu dịch vụ")
public class CTPhieuDichVuController {

    @Autowired
    private CTPhieuDichVuService service;

    @Autowired
    private PhieuDichVuRepository phieuDichVuRepository;

    @Autowired
    private LoaiDichVuRepository loaiDichVuRepository;

    @Operation(summary = "Lấy tất cả chi tiết phiếu dịch vụ")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CTPhieuDichVu> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chi tiết phiếu dịch vụ nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy chi tiết phiếu dịch vụ theo mã")
    @GetMapping("/{soPhieuDV}/{maLDV}")
    public ResponseEntity<?> getById(@PathVariable String soPhieuDV, @PathVariable String maLDV) {
        CTPhieuDichVuId id = new CTPhieuDichVuId(soPhieuDV, maLDV);
        Optional<CTPhieuDichVu> ct = service.getById(id);
        return ct.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy chi tiết phiếu dịch vụ."));
    }

    @Operation(summary = "Tạo hoặc cập nhật chi tiết phiếu dịch vụ")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody CTPhieuDichVu ct, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        try {
            var saved = service.save(ct);
            return ResponseEntity.status(
                    ct.getId() == null ? HttpStatus.CREATED : HttpStatus.OK
            ).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @Operation(summary = "Xóa chi tiết phiếu dịch vụ")
    @DeleteMapping("/{soPhieuDV}/{maLDV}")
    public ResponseEntity<?> delete(@PathVariable String soPhieuDV, @PathVariable String maLDV) {
        CTPhieuDichVuId id = new CTPhieuDichVuId(soPhieuDV, maLDV);
        Optional<CTPhieuDichVu> ct = service.getById(id);
        if (ct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa thành công.");
    }
}
