package HotWhellShop_Spring_react.util.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Binding;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import HotWhellShop_Spring_react.domain.RestResponse;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public ResponseEntity<RestResponse<Object>> EmailAlreadyExistsException(
            EmailAlreadyExistsException ex) {

        RestResponse<Object> res = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .error("Email FAIL")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validation(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        RestResponse<Object> res = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Dữ liệu đầu vào không hợp lệ") // Thông báo chung
                .error("Validation Error")
                .data(errors) // Đưa cái Map lỗi này vào data
                .build();
        return ResponseEntity.badRequest().body(res);
    }

}
