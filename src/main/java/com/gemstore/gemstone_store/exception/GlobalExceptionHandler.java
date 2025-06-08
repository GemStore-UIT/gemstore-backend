package com.gemstore.gemstone_store.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý lỗi validation (form, json body...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> Map.of(
                        "field", fieldError.getField(),
                        "error", fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Lỗi không xác định"
                ))
                .collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 400);
        response.put("message", "Validation failed");
        response.put("timestamp", System.currentTimeMillis());
        response.put("errors", errors);

        log.warn("Validation error: {}", errors);

        return ResponseEntity.badRequest().body(response);
    }


    // Xử lý lỗi vi phạm ràng buộc CSDL (khóa ngoại, duy nhất,...)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleIntegrity(DataIntegrityViolationException ex) {
        log.error("Lỗi vi phạm ràng buộc CSDL: ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Lỗi dữ liệu: " + ex.getRootCause().getMessage());
    }

    // Xử lý tất cả các lỗi khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {
        log.error("Lỗi hệ thống: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Đã xảy ra lỗi hệ thống. Vui lòng liên hệ quản trị viên!");
    }
}
