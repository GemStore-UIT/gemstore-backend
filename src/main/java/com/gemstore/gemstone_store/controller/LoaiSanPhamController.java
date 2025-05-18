package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.LoaiSanPham;
import com.gemstore.gemstone_store.service.LoaiSanPhamService;
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
@RequestMapping("/api/loaisanpham")
@Tag(name = "Loại sản phẩm", description = "CRUD loại sản phẩm")
public class LoaiSanPhamController {

    @Autowired
    private LoaiSanPhamService service;

    @Operation(summary = "Lấy tất cả loại sản phẩm")
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<LoaiSanPham> list = service.getAll();
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có loại sản phẩm nào.");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lấy loại sản phẩm theo mã")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Optional<LoaiSanPham> loaiSanPham = service.getById(id);
        return loaiSanPham.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy loại sản phẩm với mã: " + id));
    }

    @Operation(summary = "Tạo hoặc cập nhật loại sản phẩm")
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody LoaiSanPham lsp, BindingResult result){
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Lỗi: " + errors);
        }
        LoaiSanPham saved = service.save(lsp);
        return ResponseEntity.status(
                lsp.getMaLSP() == null ? HttpStatus.CREATED : HttpStatus.OK
        ).body(saved);
    }

    @Operation(summary = "Xóa loại sản phẩm theo mã")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam String id){
        Optional<LoaiSanPham> loaiSanPham = service.getById(id);
        if(loaiSanPham.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy loại sản phẩm để xóa.");
        }
        service.delete(id);
        return ResponseEntity.ok("Xóa loại sản phẩm thành công.");
    }

}
