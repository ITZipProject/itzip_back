package darkoverload.itzip.sample.swagger;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//스웨거 관련예시 코드
//자세한 문서는 github-wiki를 참고해주세용
@RequiredArgsConstructor
@RestController
@RequestMapping("/swagger")
public class SwaggerUIExampleController {

    //아래 4가지는 자동으로 200에러로 처리해준다.
    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/response")
    public ResponseEntity<String> index3() {
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @GetMapping(value ="/user/{id}")
    public User getUser(@PathVariable Long id) {
        User user = new User();
        return user;
    }

    @PostMapping(value ="/user2/{id}")
    public User postUser2(@PathVariable Long id) {
        User user = new User();
        return user;
    }

    //맞는 방식
    /*
    @ResponseCodeAnnotation(CommonResponseCode.CREATED) 이를 통해서 swagger에 응답 문서 추가
    @ExceptionCodeAnnotations({CommonExceptionCode.BAD_GATEWAY, CommonExceptionCode.BAD_REQUEST}) 이를 통해 2가지 예외 추가
     */
    @GetMapping("/user")
    @ResponseCodeAnnotation(CommonResponseCode.CREATED)
    @ExceptionCodeAnnotations({CommonExceptionCode.BAD_GATEWAY, CommonExceptionCode.BAD_REQUEST})
    public ResponseEntity<User> getUser() {
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //맞는 방식
    /*
    @ResponseCodeAnnotation(CommonResponseCode.CREATED) 이를 통해서 swagger에 응답 문서 추가
    @ExceptionCodeAnnotations({CommonExceptionCode.BAD_GATEWAY, CommonExceptionCode.BAD_REQUEST}) 이를 통해 2가지 예외 추가
    throw new RestApiException(CommonExceptionCode.BAD_REQUEST); 를 통해서 예외 처리
     */
    @GetMapping("/user2")
    @ResponseCodeAnnotation(CommonResponseCode.CREATED)
    @ExceptionCodeAnnotations({CommonExceptionCode.BAD_GATEWAY, CommonExceptionCode.BAD_REQUEST})
    public ResponseEntity<User> getUser2() {
        User user = new User();
        if (user == null) {
            throw new RestApiException(CommonExceptionCode.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //맞는 방식
    //@ResponseCodeAnnotation(CommonResponseCode.CREATED) 를통해서 swagger에 표시를 했다.
    @GetMapping("/user3")
    @ResponseCodeAnnotation(CommonResponseCode.CREATED)
    public ResponseEntity<User> getUser3() {
        User user = new User();
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //맞는 방식
    //@ResponseCodeAnnotation(CommonResponseCode.ACCEPTED) 를통해서 swagger에 표시를 했다.
    @GetMapping("/user4")
    @ResponseCodeAnnotation(CommonResponseCode.ACCEPTED)
    public ResponseEntity<User> getUser4() {
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    /*스웨거 문서에 @ResponseCodeAnnotation을 통해서 아래 내용이 출력되나 실제 응답에는 아무것도 안뜸
        {
          "status": "204 NO_CONTENT",
          "msg": "요청이 성공적으로 처리되었으나 반환할 내용이 없습니다",
          "data": "string",
          "code": "NO_CONTENT"
        }
     */
    @GetMapping("/user5")
    @ResponseCodeAnnotation(CommonResponseCode.NO_CONTENT)
    public ResponseEntity getUser5() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //이렇게 사용하면 안됨
    /*현제 300번대를 설정하지 않았기 때문에 응답 형식이 다음과 같이 날라감
        {
          "id": 1,
          "name": "John Doe",
          "email": "john.doe@gmail.com"
        }
     */
    @GetMapping("/user6")
    public ResponseEntity<User> getUser6() {
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    //이렇게 사용하면 안됨 에러는 RestApiException을 사용해서 return해야함
    //요청이 404에러로 날라가긴하지만 return형식이 맞지 않음
    @GetMapping("/user7")
    public ResponseEntity<User> getUser7() {
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
    }

    //이렇게 사용하면 안됨 에러는 RestApiException을 사용해서 return해야함
    //요청이 402에러로 날라가긴하지만 return형식이 맞지 않음
    @GetMapping("/user8")
    public ResponseEntity<User> getUser8() {
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }

    //이렇게 사용하면 안됨 에러는 RestApiException을 사용해서 return해야함
    //요청이 403에러로 날라가긴하지만 return형식이 맞지 않음
    @GetMapping("/user9")
    public ResponseEntity<User> getUser9() {
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
    }

    //이렇게 사용하면 안됨 에러는 RestApiException을 사용해서 return해야함
    //요청이 401에러로 날라가긴하지만 return형식이 맞지 않음
    @GetMapping("/user10")
    public ResponseEntity<User> getUser10() {
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
    }

    //이렇게 사용하면 안됨 에러는 RestApiException을 사용해서 return해야함
    //요청이 500에러로 날라가긴하지만 return형식이 맞지 않음
    @GetMapping("/user11")
    public ResponseEntity<User> getUser11() {
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    class User {
        Integer id = 2759;
        String name = "gnoDgnoD";
        String email = " Aleph@tistory.com";
    }
}