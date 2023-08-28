package team.hanaro.hanamate.infra;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.global.Response;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Response response;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return response.invalidFields(Helper.refineErrors(bindingResult));
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> handleJsonMappingException(JsonMappingException ex) {
        return response.fail("Invalid JSON format or data", HttpStatus.BAD_REQUEST);
    }
}
