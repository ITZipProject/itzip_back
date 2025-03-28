package darkoverload.itzip.feature.techinfo.application.generator;

import java.util.Objects;

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
