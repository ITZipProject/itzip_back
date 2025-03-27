package darkoverload.itzip.feature.techinfo.application.type;

import darkoverload.itzip.feature.techinfo.application.generator.UpperCaseGenerator;

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
            throw new IllegalArgumentException(String.format("'%s'에 해당하는 정렬 타입을 찾을 수 없습니다.", type));
        }
    }

}
