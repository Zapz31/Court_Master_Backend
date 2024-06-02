package com.swp391.Court_Master.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IndexOutOfBoundsException.class)  // Lỗi runtime exception ở bất cứ nơi nào trong chương trình đều sẽ được bắt ở đây
    ResponseEntity<String> handlingRuntimeException (RuntimeException exception){
        return ResponseEntity.badRequest().body(exception.getMessage()); // Loi do du lieu nguoi dung nhap vao se la badRequest
    }
}
