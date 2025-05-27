package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.DonViTinh;
import com.gemstore.gemstone_store.model.LoaiDichVu;
import com.gemstore.gemstone_store.repository.LoaiDichVuRepository;
import com.gemstore.gemstone_store.service.LoaiDichVuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LoaiDichVuServiceImpl implements LoaiDichVuService {

    @Autowired
    private LoaiDichVuRepository repo;

    @Override
    public List<LoaiDichVu> getAll() {
        log.info("Lấy tất cả loại dịch vụ");
        List<LoaiDichVu> list = repo.findAll();
        log.debug("Số lượng loại dịch vụ trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<LoaiDichVu> getById(String id) {
        log.info("Tìm loại dịch vụ với id={}", id);
        Optional<LoaiDichVu> ldv = repo.findById(id);
        if (ldv.isEmpty()) {
            log.warn("Không tìm thấy loại dịch vụ với id={}", id);
        }
        return ldv;
    }

    @Override
    public LoaiDichVu save(LoaiDichVu ldv) {
        log.info("Lưu loại dịch vụ mới/cập nhật: {}", ldv);
        LoaiDichVu saved = repo.save(ldv);
        log.info("Lưu thành công loại dịch vụ với id={}", saved.getMaLDV());
        return saved;
    }

    @Override
    public void delete(String id) {
        log.info("Xóa loại dịch vụ với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công loại dịch vụ với id={}", id);
    }

    @Override
    public List<LoaiDichVu> getAllByName(String name){
        return repo.findByTenDichVuContainingIgnoreCase(name);
    }
}
