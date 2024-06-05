package com.swp391.Court_Master.Exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.swp391.Court_Master.dto.request.Respone.MessageResponse;

import io.jsonwebtoken.MalformedJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IndexOutOfBoundsException.class) // Lỗi runtime exception ở bất cứ nơi nào trong chương
                                                               // trình đều sẽ được bắt ở đây
    ResponseEntity<String> handlingRuntimeException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage()); // Loi do du lieu nguoi dung nhap vao se la
                                                                         // badRequest
    }

    @ExceptionHandler(value = AuthenticationException.class)
    ResponseEntity<MessageResponse> handlingUserNameNotFoundException (AuthenticationException exception){
        MessageResponse mess = new MessageResponse("Invalid email or Phone number");
      return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST); 
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageResponse> handleAccessDeniedException(AccessDeniedException ex) {
        MessageResponse mess = new MessageResponse("You don't have permission to access this resource");
        return new ResponseEntity<>(mess, HttpStatus.FORBIDDEN);
    }

}
