package springboot.project.worktime.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import springboot.project.worktime.global.response.Response;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* 1. @Valid 검증 실패 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            builder.append("[")
                    .append(fieldError.getDefaultMessage())
                    .append(" : ")
                    .append(fieldError.getField())
                    .append("(입력값 : ")
                    .append(fieldError.getRejectedValue())
                    .append(")")
                    .append("]");
        }

        Response rep = Response.badRequest(builder.toString());
        System.out.println(rep.toString());

        return ResponseEntity
                .badRequest()
                .body(Response.badRequest(builder.toString()));

    }

    /* 2. DB 제약조건 위반 */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(
            DataIntegrityViolationException e
    ) {
        log.error("[ERROR]", e);
        return ResponseEntity
                .internalServerError()
                .body(Response.internalServerError(e.getMessage()));
    }

    /* 3. 서비스에서 던진 비즈니스 예외 */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleBusiness(ResponseStatusException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(
                        Response.builder()
                                .code(e.getStatusCode().value())
                                .message(e.getReason())
                                .build()
                );
    }

    // 그 외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("[ERROR]", e);
        return ResponseEntity
                .internalServerError()
                .body(Response.internalServerError(e.getMessage()));
    }

}
