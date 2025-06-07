package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.DonViTinh;
import com.gemstore.gemstone_store.service.DonViTinhService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/donvitinh")
@Tag(name = "Đơn vị tính", description = "CRUD đơn vị tính")
public class DonViTinhController {

    @Autowired
    private DonViTinhService service;

    @Operation(summary = "Lấy tất cả đơn vị tính")
    @GetMapping
    public ResponseEntity<?> getAll(){
        log.info("API GET /api/donvitinh - Lấy tất cả đơn vị tính");
        List<DonViTinh> list = service.getAll();
        if(list.isEmpty()){
            log.warn("Không có đơn vị tính nào.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có đơn vị tính nào.");
        }
        log.debug("Trả về {} đơn vị tính", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy đơn vị tính theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        log.info("API GET /api/donvitinh/{} - Tìm đơn vị tính", id);
        Optional<DonViTinh> dvt = service.getById(id);
        return dvt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Không tìm thấy đơn vị tính với id={}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Không tìm thấy đơn vị tính với mã: " + id);
                });
    }

    @Operation(summary = "Tạo hoặc cập nhật đơn vị tính")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody DonViTinh dvt){
        log.info("API POST /api/donvitinh - Tạo hoặc cập nhật đơn vị tính: {}", dvt);
        DonViTinh saved = service.save(dvt);
        log.info("Đã lưu đơn vị tính thành công với id={}", saved.getMaDonVi());
        return ResponseEntity.status(
                dvt.getMaDonVi() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa đơn vị tính theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        log.info("API DELETE /api/donvitinh/{} - Xóa đơn vị tính", id);
        Optional<DonViTinh> dvt = service.getById(id);
        if (dvt.isEmpty()) {
            log.warn("Không tìm thấy đơn vị tính với id={} để xóa.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy đơn vị tính để xóa.");
        }
        service.delete(id);
        log.info("Đã xóa đơn vị tính thành công với id={}", id);
        return ResponseEntity.ok("Xóa đơn vị tính thành công.");
    }

}
