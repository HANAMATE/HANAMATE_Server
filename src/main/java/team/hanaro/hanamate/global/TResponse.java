package team.hanaro.hanamate.global;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TResponse<T> {

    @Getter
    @Builder
    public static class Body<T> {

        private int success;
        private T data;
        private String error;
    }

    public ResponseEntity<Body<T>> success(@Nullable T data) {
        Body body = Body.builder()
                .success(HttpStatus.OK.value())
                .data(data)
                .error("")
                .build();
        return ResponseEntity.ok(body);
    }


    public ResponseEntity<Body<T>> fail(String msg, HttpStatus status) {
        Body body = Body.builder()
                .success(status.value())
                .error(msg)
                .build();
        return ResponseEntity.ok(body);
    }


}
