package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.DonViTinh;
import com.gemstore.gemstone_store.repository.DonViTinhRepository;
import com.gemstore.gemstone_store.service.DonViTinhService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DonViTinhServiceImpl implements DonViTinhService {

    @Autowired
    private DonViTinhRepository repo;

    @Override
    public List<DonViTinh> getAll(){
        log.info("Lấy tất cả đơn vị tính");
        List<DonViTinh> list = repo.findAll();
        log.debug("Số lượng đơn vị tính trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<DonViTinh> getById(UUID id){
        log.info("Tìm đơn vị tính với id={}", id);
        Optional<DonViTinh> dvt = repo.findById(id);
        if (dvt.isEmpty()) {
            log.warn("Không tìm thấy đơn vị tính với id={}", id);
        }
        return dvt;
    }

    @Override
    public DonViTinh save(DonViTinh dvt){
        log.info("Lưu đơn vị tính mới/cập nhật: {}", dvt);
        DonViTinh saved = repo.save(dvt);
        log.info("Lưu thành công đơn vị tính với id={}", saved.getMaDonVi());
        return saved;
    }

    @Override
    public void delete(UUID id){
        log.info("Xóa đơn vị tính với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công đơn vị tính với id={}", id);
    }

    @Override
    public List<DonViTinh> getAllByName(String name){
        return repo.findByTenDonViContainingIgnoreCase(name);
    }
}
