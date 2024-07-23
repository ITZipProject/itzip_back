package darkoverload.itzip.global.config.response.code;

import org.springframework.http.HttpStatus;

/*
응답, 예외코드 형식 만약 custome을 만들경우 ResponseCode를 상속해서 사용해야 한다.
응답 코드는 커스텀을 할수 없다.
 */
public interface ResponseCode {
    //code가 담김
    String name();
    //상태가 담김
    HttpStatus getHttpStatus();
    //응답의 경우에는 data가 예외의 경우에는 예외 사유가 들어감
    String getData();
}
