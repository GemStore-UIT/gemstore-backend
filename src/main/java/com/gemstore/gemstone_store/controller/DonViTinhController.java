package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.model.DonViTinh;
import com.gemstore.gemstone_store.service.DonViTinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donvitinh")
public class DonViTinhController {

    @Autowired
    private DonViTinhService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<DonViTinh> list = service.getAll();
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có đơn vị tính nào.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Optional<DonViTinh> dvt = service.getById(id);
        return dvt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy đơn vị tính với mã: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody DonViTinh dvt){
        DonViTinh saved = service.save(dvt);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

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
