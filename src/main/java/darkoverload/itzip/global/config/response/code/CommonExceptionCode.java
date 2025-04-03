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
    FILE_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "파일 정보를 찾을 수 없습니다."),

    /**
     * image Error
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
    // 이메일 회원이 sns 로그인 시
    EMAIL_USER_SNS_LOGIN(HttpStatus.BAD_REQUEST, "이메일 회원가입 계정은 sns 로그인이 불가합니다."),
    // 구글 회원이 깃허브 로그인 시
    GOOGLE_USER_GITHUB_LOGIN(HttpStatus.BAD_REQUEST, "구글 로그인 계정입니다. 구글 로그인으로 다시 시도해주세요."),
    // 깃허브 회원이 구글 로그인 시
    GITHUB_USER_GOOGLE_LOGIN(HttpStatus.BAD_REQUEST, "깃허브 로그인 계정입니다. 깃허브 로그인으로 다시 시도해주세요."),
    // 비밀번호 재설정 시 구글 로그인 계정
    GOOGLE_LOGIN_USER(HttpStatus.BAD_REQUEST, "구글 로그인으로 회원가입한 계정은 비밀번호 재설정이 불가합니다."),
    // 비밀번호 재설정 시 깃허브 로그인 계정
    GITHUB_LOGIN_USER(HttpStatus.BAD_REQUEST, "깃허브 로그인으로 회원가입한 계정은 비밀번호 재설정이 불가합니다."),

    /**
     * Tech Info - Common
     */
    SORT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "정렬 타입을 찾을 수 없습니다."),

    /**
     * Tech Info - Blog
     */
    BLOG_NOT_FOUND(HttpStatus.NOT_FOUND, "블로그를 찾을 수 없습니다."),
    BLOG_INTRO_REQUIRED(HttpStatus.BAD_REQUEST, "블로그 소개글은 반드시 입력되어야 합니다."),

    /**
     * Tech Info - Article
     */
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "아티클을 찾을 수 없습니다."),
    ARTICLE_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "아티클 제목은 반드시 입력되어야 합니다."),
    ARTICLE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "아티클 타입을 찾을 수 없습니다."),

    /**
     * Tech Info - Comment
     */
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    COMMENT_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "댓글 본문은 반드시 입력되어야 합니다."),

    /**
     *  Tech Info - Like
     */
    ALREADY_LIKED_ARTICLE(HttpStatus.CONFLICT, "이미 해당 아티클에 좋아요를 눌렀습니다."),

    /**
     *  Tech Info - Scrap
     */
    ALREADY_SCRAP_ARTICLE(HttpStatus.CONFLICT, "이미 해당 아티클에 스크랩을 눌렀습니다."),

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
    //난이도 없음
    NOT_FOUND_DIFFICULTY(HttpStatus.NOT_FOUND, "난이도가 없습니다."),
    //퀴즈 처리 중 문제 발생
    QUIZ_PROCESSING_ERROR(HttpStatus.BAD_REQUEST, "퀴즈 처리 중 문제가 발생했습니다."),
    //퀴즈 점수가 없음
    NOT_FOUND_QUIZ_SCORE(HttpStatus.NOT_FOUND, "퀴즈 점수를 찾을 수 없습니다"),
    //퀴즈 랭킹이 없음
    NOT_FOUND_QUIZ_SCORE_RANKING(HttpStatus.NOT_FOUND, "랭킹을 찾을 수 없습니다."),

    /**
     * Algorithm Error
     */
    //sovledac TAG를 받는중 에러가 생김
    SOLVED_TAG_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "solved Tag를 업데이트하는중 문제가 생겼습니다."),
    //sovledac problem을 받는중 에러가 생김
    SOLVED_PROBLEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "solved problem를 업데이트하는중 문제가 생겼습니다."),
    //solvedac 사용자가 푼 문제를 받는중 에러가 생김
    SOLVED_USER_SOLVED_PROBLEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "solvedac 사용자가 푼 문제를 받는중 에러가 생겼습니댜."),
    //solvedac 사용자가 정보를 등록하는 중 에러가 생김
    SOLVED_USER_SOLVED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "solvedac 사용자 정보를 저장하는 중 에러가 생겼습니댜."),
    //sovledac Id를 등록해야 합니다.
    NOT_FOUND_SOLVEDAC_USER(HttpStatus.BAD_REQUEST, "solvedac user를 등록해주세요"),
    //solvedac에서 username을 찾을 수 없음
    NOT_FOUND_SOLVEDAC_USERNAME(HttpStatus.BAD_REQUEST, "solvedac에서 user이름을 찾을 수 없습니다."),
    //업데이트 하려면 기다려야 합니다.
    UPDATE_COOLDOWN(HttpStatus.CONFLICT, "마지막 업데이트로부터 시간이 지나지 않았습니다."),
    //solved.ac 서버로부터 api를 받아올수 없습니다.
    SOLVEDAC_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "solved.ac로부터 오류를 보냈습니다."),
    //solved.ac 로부터 받아온 url에 에러가 생겼습니다.
    SOLVEDAC_PROFILEIMAGE_URL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "solved.ac로부터 받은 image url에 에러가 있습니다."),

    /**
     * Resume Error
     */

    // 이력서 생성 오류
    INSERT_FAIL_RESUME(HttpStatus.BAD_REQUEST, "이력서 생성 오류"),
    // 이력서 업데이트 오류
    UPDATE_FAIL_RESUME(HttpStatus.BAD_REQUEST, "이력서 업데이트 오류"),
    // 이력서 조회 오류 발생
    NOT_FOUND_RESUME(HttpStatus.BAD_REQUEST, "이력서 정보 조회 중 오류가 발생하였습니다."),
    NOT_MATCH_RESUME_USERID(HttpStatus.NOT_FOUND, "작성자 아이디가 일치하지 않습니다."),
    // 이력서 파일 타입 오류 발생
    FILE_TYPE_RESUME_ERROR(HttpStatus.BAD_REQUEST, "파일 형식이 pdf, 워드 형식이 아닙니다."),
    NOT_FOUND_RESUME_SCRAP(HttpStatus.BAD_REQUEST, "스크랩한 채용공고를 찾을 수 없습니다."),

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