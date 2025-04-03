package darkoverload.itzip.feature.techinfo.application.generator;

import java.util.Objects;

/**
 * 문자열을 대문자로 변환하는 유틸리티 클래스입니다.
 */
public class UpperCaseGenerator {

    private UpperCaseGenerator() {
    }

    public static String generate(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("NULL 혹은 공백인 값을 변환할 수 없습니다.");
        }
        return value.toUpperCase();
    }

}
