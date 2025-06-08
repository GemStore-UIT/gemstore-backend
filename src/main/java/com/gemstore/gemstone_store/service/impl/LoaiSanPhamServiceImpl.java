package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.LoaiSanPham;
import com.gemstore.gemstone_store.repository.LoaiSanPhamRepository;
import com.gemstore.gemstone_store.service.LoaiSanPhamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {

    @Autowired
    private LoaiSanPhamRepository repo;

    @Override
    public List<LoaiSanPham> getAll() {
        log.info("Lấy tất cả loại sản phẩm");
        List<LoaiSanPham> list = repo.findAll();
        log.debug("Số lượng loại sản phẩm trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<LoaiSanPham> getById(String id) {
        log.info("Tìm loại sản phẩm với id={}", id);
        Optional<LoaiSanPham> lsp = repo.findById(id);
        if (lsp.isEmpty()) {
            log.warn("Không tìm thấy loại sản phẩm với id={}", id);
        }
        return lsp;
    }

    @Override
    public LoaiSanPham save(LoaiSanPham loaiSanPham){
        log.info("Lưu loại sản phẩm mới/cập nhật: {}", loaiSanPham);
        LoaiSanPham saved = repo.save(loaiSanPham);
        log.info("Lưu thành công loại sản phẩm với id={}", saved.getMaLSP());
        return saved;
    }

    @Override
    public void delete(String id){
        log.info("Xóa loại sản phẩm với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công loại sản phẩm với id={}", id);
    }

    @Override
    public List<LoaiSanPham> getAllByName(String name){
        return repo.findByTenLSPContainingIgnoreCase(name);
    }
}
