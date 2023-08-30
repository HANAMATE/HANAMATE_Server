package team.hanaro.hanamate.domain.User.Dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
@Component
public class UserResponse {

    @Getter
    @Builder
    private static class Header {

        String accessToken;
        String refreshToken;

    }

    @Getter
    @Builder
    private static class Body {

        private Header header;
        private int state;
        private String result;
        private String message;
        private Object data;
        private Object error;
    }

    public ResponseEntity<?> success(String accessToken, String refreshToken, Object data, String msg, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        // AccessToken을 Header에 추가
        headers.add("Authorization", "Bearer " + accessToken);

        // RefreshToken을 Header에 추가 (예시로 "X-Refresh-Token" 헤더 사용)
        headers.add("X-Refresh-Token", refreshToken);


        UserResponse.Body body = UserResponse.Body.builder()
                .header(null)
                .state(status.value())
                .data(data)
                .result("success")
                .message(msg)
                .error(Collections.emptyList())
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(body);
    }




}
