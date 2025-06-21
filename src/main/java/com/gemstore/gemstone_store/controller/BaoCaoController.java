package com.gemstore.gemstone_store.controller;

import com.gemstore.gemstone_store.dto.response.BaoCaoResponse;
import com.gemstore.gemstone_store.service.BaoCaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/baocao")
@Tag(name = "Báo cáo", description = "Báo cáo chi tiết theo tháng")
public class BaoCaoController {

    @Autowired
    private BaoCaoService service;

    @Operation(summary = "Lập báo cáo theo tháng")
    @GetMapping("/{thang}/{nam}")
    public ResponseEntity<?> getBaoCao(@RequestParam int thang, @RequestParam int nam){
        List<BaoCaoResponse> responseList = service.getBaoCao(thang, nam);
        return ResponseEntity.ok(responseList);
    }

}
