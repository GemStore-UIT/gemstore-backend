package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.DonViTinh;
import com.gemstore.gemstone_store.service.DonViTinhService;
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
@RequestMapping("/api/donvitinh")
@Tag(name = "Đơn vị tính", description = "CRUD đơn vị tính")
public class DonViTinhController {

    @Autowired
    private DonViTinhService service;

    @Operation(summary = "Lấy tất cả đơn vị tính")
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<DonViTinh> list = service.getAll();
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có đơn vị tính nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy đơn vị tính theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Optional<DonViTinh> dvt = service.getById(id);
        return dvt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy đơn vị tính với mã: " + id));
    }

    @Operation(summary = "Tạo hoặc cập nhật đơn vị tính")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody DonViTinh dvt, BindingResult result){
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        DonViTinh saved = service.save(dvt);
        return ResponseEntity.status(
                dvt.getMaDonVi() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa đơn vị tính theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam String id){
        Optional<DonViTinh> dvt = service.getById(id);
        if(dvt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy đơn vị tính để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa đơn vị tính thành công.");
    }

}
