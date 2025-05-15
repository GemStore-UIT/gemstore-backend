package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.CTPhieuBanHang;
import com.gemstore.gemstone_store.model.PhieuBanHang;
import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.model.id.CTPhieuBanHangId;
import com.gemstore.gemstone_store.repository.PhieuBanHangRepository;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.CTPhieuBanHangService;
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
@RequestMapping("/api/ctphieubanhang")
@Tag(name = "Chi tiết phiếu bán hàng", description = "CRUD chi tiết phiếu bán hàng")
public class CTPhieuBanHangController {

    @Autowired
    private CTPhieuBanHangService service;

    @Autowired
    private PhieuBanHangRepository phieuBanHangRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Operation(summary = "Lấy tất cả chi tiết phiếu bán hàng")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CTPhieuBanHang> list = service.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chi tiết phiếu bán hàng nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy chi tiết phiếu bán hàng theo mã")
    @GetMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> getById(@PathVariable String maSP, @PathVariable String soPhieu) {
        CTPhieuBanHangId id = new CTPhieuBanHangId(maSP, soPhieu);
        Optional<CTPhieuBanHang> ct = service.getById(id);
        return ct.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy chi tiết phiếu bán hàng."));
    }

    @Operation(summary = "Tạo hoặc cập nhật chi tiết phiếu bán hàng")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody CTPhieuBanHang ct, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        try {

            PhieuBanHang pbh = phieuBanHangRepository.findById(ct.getPhieuBanHang().getSoPhieuBH())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy PhieuBanHang"));
            SanPham sp = sanPhamRepository.findById(ct.getSanPham().getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy SanPham"));

            ct.setPhieuBanHang(pbh);
            ct.setSanPham(sp);
            ct.setId(new CTPhieuBanHangId(ct.getPhieuBanHang().getSoPhieuBH(), ct.getSanPham().getMaSanPham()));

            CTPhieuBanHang saved = service.save(ct);
            return ResponseEntity.status(
                    ct.getId() == null ? HttpStatus.CREATED : HttpStatus.OK
            ).body(saved);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @Operation(summary = "Xóa chi tiết phiếu bán hàng theo mã")
    @DeleteMapping("/{maSP}/{soPhieu}")
    public ResponseEntity<?> delete(@PathVariable String maSP, @PathVariable String soPhieu) {
        CTPhieuBanHangId id = new CTPhieuBanHangId(maSP, soPhieu);
        Optional<CTPhieuBanHang> ct = service.getById(id);
        if (ct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa thành công.");
    }
}
