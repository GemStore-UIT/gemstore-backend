package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.ThamSo;
import com.gemstore.gemstone_store.repository.ThamSoRepository;
import com.gemstore.gemstone_store.service.ThamSoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ThamSoServiceImpl implements ThamSoService {

    @Autowired
    private ThamSoRepository repo;

    @Override
    public List<ThamSo> getAll() {
        log.info("Lấy tất cả tham số");
        List<ThamSo> list = repo.findAll();
        log.debug("Số lượng tham số trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<ThamSo> getByName(String name) {
        log.info("Tìm tham số với tên={}", name);
        Optional<ThamSo> ts = repo.findById(name);
        if (ts.isEmpty()) {
            log.warn("Không tìm thấy tham số với tên={}", name);
        }
        return ts;
    }

    @Override
    public ThamSo save(ThamSo ts) {
        log.info("Lưu tham số mới/cập nhật: {}", ts);
        ThamSo saved = repo.save(ts);
        log.info("Lưu thành công tham số với tên={}", saved.getTenThamSo());
        return saved;
    }

    @Override
    public void delete(String name) {
        log.info("Xóa tham số với tên={}", name);
        repo.deleteById(name);
        log.info("Xóa thành công tham số với tên={}", name);
    }

}
