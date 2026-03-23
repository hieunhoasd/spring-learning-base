package HotWhellShop_Spring_react.util.error.Exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import HotWhellShop_Spring_react.domain.Respsone.RestResponse;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public ResponseEntity<RestResponse<Object>> EmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        RestResponse<Object> res = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .message(ex.getMessage())
                .error("Email fail")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> BadCredentialsException(UsernameNotFoundException ex) {
        RestResponse<Object> res = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .message(ex.getMessage())
                .error("Username/password error")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validation(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fielderror = result.getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : fielderror) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        RestResponse<Object> res = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Dữ liệu đầu vào không hợp lệ")
                .error("Validation Error")
                .data(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RestResponse<Object>> JwtException(AuthenticationException ex) {
        RestResponse<Object> res = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .message(ex.getMessage())
                .error("error")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(BadJwtException.class)
    public ResponseEntity<RestResponse<Object>> BadJwtException(Exception ex) {
        RestResponse<Object> res = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .message(ex.getMessage())
                .error("error")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

}
