package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.dto.response.CTPhieuBanHangResponse;
import com.gemstore.gemstone_store.dto.response.CTPhieuDichVuResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("API GET /api/ctphieudichvu - Lấy tất cả chi tiết phiếu dịch vụ");
        List<CTPhieuDichVuResponse> list = service.getAll();
        if (list.isEmpty()) {
            log.warn("Không có chi tiết phiếu dịch vụ nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có chi tiết phiếu dịch vụ nào.");
        }
        log.debug("Trả về {} chi tiết phiếu dịch vụ", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy chi tiết phiếu dịch vụ theo mã")
    @GetMapping("/{soPhieuDV}/{maLDV}")
    public ResponseEntity<?> getById(@PathVariable UUID soPhieuDV, @PathVariable UUID maLDV) {
        log.info("API GET /api/ctphieudichvu/{}/{} - Tìm chi tiết phiếu dịch vụ", soPhieuDV, maLDV);
        CTPhieuDichVuId id = new CTPhieuDichVuId(soPhieuDV, maLDV);
        Optional<CTPhieuDichVuResponse> ct = service.getById(id);
        return ct.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy chi tiết phiếu dịch vụ với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy chi tiết phiếu dịch vụ.");
                });
    }

    @Operation
    @GetMapping("/by-phieudichvu/{soPhieuDV}")
    public ResponseEntity<?> getAllByPhieuDV(@PathVariable UUID soPhieuDV){
        log.info("API GET /api/ctphieudichvu//by-phieudichvu/{}- Tìm tất cả chi tiết của một phiếu dịch vụ", soPhieuDV);
        List<CTPhieuDichVuResponse> cts = service.getAllByPhieuDV(soPhieuDV);
        if(cts.isEmpty()){
            log.warn("Không tìm thấy chi tiết phiếu dịch vụ của phiếu {}", soPhieuDV);
        }
        return ResponseEntity.ok(cts);
    }

    @Operation(summary = "Tạo hoặc cập nhật chi tiết phiếu dịch vụ")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody CTPhieuDichVu ct) {
        log.info("API POST /api/ctphieudichvu - Tạo/cập nhật chi tiết phiếu dịch vụ: {}", ct);
        CTPhieuDichVu saved = service.save(ct);
        log.info("Đã lưu chi tiết phiếu dịch vụ thành công với id={}", saved.getId());
        return ResponseEntity.status(
                ct.getId() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa chi tiết phiếu dịch vụ")
    @DeleteMapping("/{soPhieuDV}/{maLDV}")
    public ResponseEntity<?> delete(@PathVariable UUID soPhieuDV, @PathVariable UUID maLDV) {
        log.info("API DELETE /api/ctphieudichvu/{}/{} - Xóa chi tiết phiếu dịch vụ", soPhieuDV, maLDV);
        CTPhieuDichVuId id = new CTPhieuDichVuId(soPhieuDV, maLDV);
        Optional<CTPhieuDichVuResponse> ct = service.getById(id);
        if (ct.isEmpty()) {
            log.warn("Không tìm thấy chi tiết phiếu dịch vụ với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa chi tiết phiếu dịch vụ thành công với id={}", id);
        return ResponseEntity.ok("Xóa thành công.");
    }
}
