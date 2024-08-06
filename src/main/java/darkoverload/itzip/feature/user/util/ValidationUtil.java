package darkoverload.itzip.feature.user.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BindingResult 필드 오류 메시지 매핑 클래스 (미사용)
 *
 * @deprecated field error 내용 반환이 불필요하여 미사용 처리
 */
@Component
public class ValidationUtil {
    /**
     * BindingResult에서 발생한 필드 오류를 가져와 필드 이름과 오류 메시지를 매핑한 Map을 반환
     *
     * @param bindingResult 유효성 검사 결과를 담고 있는 BindingResult 객체
     * @return 필드 이름과 오류 메시지를 매핑한 Map 객체
     */
    public Map<String, String> getBindingError(BindingResult bindingResult) {
        // BindingResult에서 필드 오류 목록을 가져옴
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // 필드 이름과 오류 메시지를 저장할 Map 객체 생성
        Map<String, String> errors = new HashMap<>();

        // 필드 오류 목록을 순회하며 각 필드 이름과 해당 오류 메시지를 Map에 추가
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // 필드 이름과 오류 메시지를 매핑한 Map 반환
        return errors;
    }

}
