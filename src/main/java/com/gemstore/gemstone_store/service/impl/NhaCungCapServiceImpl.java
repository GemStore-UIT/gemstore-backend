package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.NhaCungCap;
import com.gemstore.gemstone_store.repository.NhaCungCapRepository;
import com.gemstore.gemstone_store.service.NhaCungCapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {

    @Autowired
    private NhaCungCapRepository repo;

    @Override
    public List<NhaCungCap> getAll() {
        log.info("Lấy tất cả nhà cung cấp");
        List<NhaCungCap> list = repo.findAll();
        log.debug("Số lượng nhà cung cấp trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<NhaCungCap> getById(String id) {
        log.info("Tìm nhà cung cấp với id={}", id);
        Optional<NhaCungCap> ncc = repo.findById(id);
        if (ncc.isEmpty()) {
            log.warn("Không tìm thấy nhà cung cấp với id={}", id);
        }
        return ncc;
    }

    @Override
    public NhaCungCap save(NhaCungCap ncc) {
        log.info("Lưu nhà cung cấp mới/cập nhật: {}", ncc);
        NhaCungCap saved = repo.save(ncc);
        log.info("Lưu thành công nhà cung cấp với id={}", saved.getMaNCC());
        return saved;
    }

    @Override
    public void delete(String id) {
        log.info("Xóa nhà cung cấp với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công nhà cung cấp với id={}", id);
    }
}
