package springboot.project.worktime.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private Integer code;

    private String message;

    private Object data;

    public static Response ok(String msg, Object data) {
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message(msg)
                .data(data)
                .build();
    }

    public static Response ok(Object data) {
        return ok(null, data);
    }

    public static Response ok(String msg) {
        return ok(msg, null);
    }

    public static Response badRequest(String msg, Object data) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(msg)
                .data(data)
                .build();
    }

    public static Response badRequest(Object data) {
        return badRequest(null, data);
    }

    public static Response badRequest(String msg) {
        return badRequest(msg, null);
    }

    public static Response internalServerError(String msg, Object data) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(msg)
                .data(data)
                .build();
    }

    public static Response internalServerError(Object data) {
        return internalServerError(null, data);
    }

    public static Response internalServerError(String msg) {
        return internalServerError(msg, null);
    }

}
