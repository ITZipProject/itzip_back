package darkoverload.itzip.feature.techinfo.application.type;

import darkoverload.itzip.feature.techinfo.application.generator.UpperCaseGenerator;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

public enum SortType {

    NEWEST,
    OLDEST,
    VIEW_COUNT,
    LIKE_COUNT;

    public static SortType from(String type) {
        final String normalizedType = UpperCaseGenerator.generate(type);
        validate(normalizedType);
        return valueOf(normalizedType);
    }

    public static void validate(String type) {
        try {
            valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new RestApiException(CommonExceptionCode.SORT_TYPE_NOT_FOUND);
        }
    }

}
