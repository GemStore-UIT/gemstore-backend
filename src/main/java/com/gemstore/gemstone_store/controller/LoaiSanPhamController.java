package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.DonViTinh;
import com.gemstore.gemstone_store.model.LoaiSanPham;
import com.gemstore.gemstone_store.service.LoaiSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loaisanpham")
public class LoaiSanPhamController {

    @Autowired
    private LoaiSanPhamService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<LoaiSanPham> list = service.getAll();
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có loại sản phẩm nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Optional<LoaiSanPham> loaiSanPham = service.getById(id);
        return loaiSanPham.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy loại sản phẩm với mã: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody LoaiSanPham loaiSanPham){
        LoaiSanPham saved = service.save(loaiSanPham);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

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
