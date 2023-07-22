package team.hanaro.hanamate.domain.Login;

import lombok.*;

//Entity는 아닌거 같은데 비슷함
//상태코드, 응답메세지, 데이터로 형식을 갖춰서 클라이언트에게 응답을 해주기 위해서 DefaultRes 클래스를 만들었다.

//ResponseEntity의 진짜 상태코드는 DefaultRes의 status에 넣어준다.
//모든 응답메세지에 한국말로 넣어준다.
//응답 데이터가 있다면 제네릭 타입을 이용해서 데이터에 넣어준다.

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class DefaultRes<T>{

    private int statusCode;
    private String responseMessage;
    private T data;

    public DefaultRes(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage, final T t) {
        return DefaultRes.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

}
