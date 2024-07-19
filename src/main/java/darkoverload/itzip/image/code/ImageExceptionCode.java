package darkoverload.itzip.image.code;

import darkoverload.itzip.global.config.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ImageExceptionCode implements ResponseCode {
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
    IMAGE_RESIZE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 에러"),

    // 임시 이미지 x
    IMAGE_NOT_TEMP(HttpStatus.BAD_REQUEST, "이미지 에러");

    private final HttpStatus httpStatus;
    private final String data;

}
