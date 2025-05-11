package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.CTPhieuDichVu;
import com.gemstore.gemstone_store.model.id.CTPhieuDichVuId;
import com.gemstore.gemstone_store.service.CTPhieuDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ctphieudichvu")
public class CTPhieuDichVuController {

    @Autowired
    private CTPhieuDichVuService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CTPhieuDichVu> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chi tiết phiếu dịch vụ nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{soPhieuDV}/{maLDV}")
    public ResponseEntity<?> getById(@PathVariable String soPhieuDV, @PathVariable String maLDV) {
        CTPhieuDichVuId id = new CTPhieuDichVuId(soPhieuDV, maLDV);
        Optional<CTPhieuDichVu> ct = service.getById(id);
        return ct.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy chi tiết phiếu dịch vụ."));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody CTPhieuDichVu ct) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(ct));
    }

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
