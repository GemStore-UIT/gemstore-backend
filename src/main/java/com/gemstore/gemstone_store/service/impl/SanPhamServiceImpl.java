package com.gemstore.gemstone_store.service.impl;

import com.gemstore.gemstone_store.model.SanPham;
import com.gemstore.gemstone_store.repository.SanPhamRepository;
import com.gemstore.gemstone_store.service.SanPhamService;
import com.gemstore.gemstone_store.service.ThamSoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository repo;

    @Autowired
    private ThamSoService thamSoService;

    @Override
    public List<SanPham> getAll() {
        log.info("Lấy tất cả sản phẩm");
        List<SanPham> list = repo.findAll();
        log.debug("Số lượng sản phẩm trả về: {}", list.size());
        return list;
    }

    @Override
    public Optional<SanPham> getById(UUID id) {
        log.info("Tìm sản phẩm với id={}", id);
        Optional<SanPham> sp = repo.findById(id);
        if (sp.isEmpty()) {
            log.warn("Không tìm thấy sản phẩm với id={}", id);
        }
        return sp;
    }

    @Override
    public SanPham save(SanPham sp) {
        log.info("Lưu sản phẩm mới/cập nhật: {}", sp);
        var optThamSo = thamSoService.getByName("SoLuongTonToiThieu");
        if(optThamSo.isEmpty()) {
            log.error("Không tìm thấy tham số 'SoLuongTonToiThieu' trong hệ thống!");
            throw new IllegalArgumentException("Không tìm thấy tham số 'Số lượng tồn tối thiểu'");
        }
        int toiThieu = optThamSo.get().getGiaTri();
        // Nếu field là Integer thì cần kiểm tra null
        if (sp.getTonKho() == null) {
            log.warn("Số lượng tồn kho không được null!");
            throw new IllegalArgumentException("Số lượng tồn kho không được để trống");
        }
        if(sp.getTonKho() < toiThieu)
            throw new IllegalArgumentException("Số lượng tồn kho phải lớn hơn hoặc bằng: " + toiThieu);
        SanPham saved = repo.save(sp);
        log.info("Lưu thành công sản phẩm với id={}", saved.getMaSanPham());
        return saved;
    }


    @Override
    public void delete(UUID id) {
        log.info("Xóa sản phẩm với id={}", id);
        repo.deleteById(id);
        log.info("Xóa thành công sản phẩm với id={}", id);
    }

    @Override
    public List<SanPham> getAllByName(String name){
        return repo.findByTenSanPhamContainingIgnoreCase(name);
    }
}
