package darkoverload.itzip.global.config.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/*
예외 응답 코드 보통 사용하는 예외 응답 코드를 정리해뒀다.
 */
@Getter
@RequiredArgsConstructor
public enum CommonExceptionCode implements ResponseCode {

    /**
     * job_info error
     */
    JOB_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "채용 정보를 찾을 수 없습니다."),


    /**
     * school Error
     */
    SCHOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "학교 정보를 찾을 수 없습니다"),

    /**
     * file error
     */
    FILE_ERROR(HttpStatus.BAD_REQUEST, "파일 에러"),

    /**
     *  image Error
     */
    // 파일 전역 IO Error 처리
    IMAGE_ERROR(HttpStatus.BAD_REQUEST, "이미지 업로드 에러"),

    // 파일 못찾는 경우 ex) null
    IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "이미지를 찾을 수 없습니다"),

    // 파일 형식을 지키지 않은 경우
    IMAGE_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "이미지 형식이 올바르지 않습니다"),

    // 이미지 리사이즈 실패
    IMAGE_RESIZE_ERROR(HttpStatus.BAD_REQUEST, "이미지 업로드 에러"),

    // 임시 이미지 x
    IMAGE_NOT_TEMP(HttpStatus.BAD_REQUEST, "이미지 에러"),

    /**
     * Token Error
     */
    // TOKEN 만료
    JWT_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    // Token 오류
    JWT_INVALID_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    // 토큰 오류
    JWT_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    // 변조된 토큰
    JWT_UNSUPPORTED_ERROR(HttpStatus.UNAUTHORIZED, "변조된 토큰입니다."),
    // 알 수 없는 오류
    JWT_INTERNAL_ERROR(HttpStatus.UNAUTHORIZED, "알 수 없는 오류가 발생했습니다."),

    /**
     * User Error
     */
    // 사용 중인 이메일
    EXIST_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다."),
    // 사용 중인 닉네임
    EXIST_NICKNAME_ERROR(HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임입니다."),
    // 올바르지 않은 요청값
    FILED_ERROR(HttpStatus.BAD_REQUEST, "요청값이 올바르지 않습니다."),
    // 이메일 인증번호 불일치
    NOT_MATCH_AUTH_CODE(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),
    // 이메일 비밀번호 불일치
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "이메일과 비밀번호가 일치하지 않습니다."),
    // 유저를 찾을 수 없음
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),

    /**
     * TechInfo Error
     */
    // ID에 해당하는 블로그를 찾을 수 없는 경우
    NOT_FOUND_BLOG(HttpStatus.NOT_FOUND, "블로그를 찾을 수 없습니다."),
    // ID에 해당하는 게시물를 찾을 수 없는 경우
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "포스트를 찾을 수 없습니다."),
    // ID에 해당하는 댓글을 찾을 수 없는 경우
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    // 해당 블로그에 게시물이 없는 경우
    NOT_FOUND_POSTS_FOR_BLOG(HttpStatus.NOT_FOUND, "해당 블로그에 대한 게시물을 찾을 수 없습니다."),
    // 해당 카테고리에 게시물이 없는 경우
    NOT_FOUND_POSTS_FOR_CATEGORY(HttpStatus.NOT_FOUND, "해당 카테고리에 대한 게시물을 찾을 수 없습니다."),
    // 잘못된 형식의 카테고리 ID가 입력된 경우
    INVALID_CATEGORY_ID(HttpStatus.BAD_REQUEST, "잘못된 카테고리 ID 형식입니다."),
    // 잘못된 형식의 포스트 ID가 입력된 경우
    INVALID_POST_ID(HttpStatus.BAD_REQUEST, "잘못된 POST ID 형식입니다."),

    /**
     * Quiz Error
     */
    //퀴즈를 찾을 수 없음
    NOT_FOUND_QUIZ(HttpStatus.NOT_FOUND, "문제를 찾을 수 없습니다."),
    //퀴즈를 풀지 않았음
    QUIZ_UNSOLVED(HttpStatus.NOT_FOUND, "퀴즈가 풀리지 않았습니다."),
    //정답을 맞춘적 없음
    ANSWER_NOT_CORRECT(HttpStatus.BAD_REQUEST, "정답을 맞춘 적이 없습니다."),
    //이미 퀴즈에 포인트를 줬음
    POINT_ALREADY_GIVEN(HttpStatus.BAD_REQUEST, "이미 이 퀴즈에 포인트를 부여했습니다."),
    //이미 정답을 맞췄음
    ALREADY_CORRECT(HttpStatus.BAD_REQUEST, "이미 정답을 맞췄습니다."),
    //카테고리가 없음
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "카테고리가 없습니다."),

    /**
     * Resume Error
     */

    // 이력서 생성 오류
    INSERT_FAIL_RESUME(HttpStatus.BAD_REQUEST, "이력서 생성 오류"),
    // 이력서 업데이트 오류
    UPDATE_FAIL_RESUME(HttpStatus.BAD_REQUEST, "이력서 업데이트 오류"),

    /**
     * MongoDb
     */
    //몽고 저장소 에러
    MONGO_DB_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "몽고 저장소에서 에러가 생겼습니다."),


    /**
     * 4** client
     */
    // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 매개 변수가 포함됨"),
    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다"),
    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다"),
    // 404
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다"),
    // 405
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원되지 않는 메서드입니다"),
    // 408
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "요청 시간이 초과되었습니다"),
    // 415
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원되지 않는 미디어 타입입니다"),

    /**
     * 5** Server Error
     */
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다"),
    // 502
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "잘못된 게이트웨이입니다"),
    // 503
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "서비스를 사용할 수 없습니다"),
    // 504
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "게이트웨이 시간 초과입니다"),
    // 505
    HTTP_VERSION_NOT_SUPPORTED(HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "HTTP 버전을 지원하지 않습니다"),
    // 507
    INSUFFICIENT_STORAGE(HttpStatus.INSUFFICIENT_STORAGE, "저장 공간이 부족합니다"),
    // 511
    NETWORK_AUTHENTICATION_REQUIRED(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "네트워크 인증이 필요합니다");

    private final HttpStatus httpStatus;
    private final String data;
}