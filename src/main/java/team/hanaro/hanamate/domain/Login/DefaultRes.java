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

//상태코드, 응답메세지, 데이터로 형식을 갖춰서 클라이언트에게 응답을 해주기 위해서 DefaultRes 클래스를 만들었음
public class DefaultRes<T>{

    private int statusCode;
    private String responseMessage;
    private T data;

    public DefaultRes(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

//    static 메소드로 만든 이유는 DefaultRes()형태로 바로 사용하기 위해서 했던 것이고, static으로 사용했기 때문에 제네릭 메소드 형태로 만들었음. (제네릭 메소드 학습하기)
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
