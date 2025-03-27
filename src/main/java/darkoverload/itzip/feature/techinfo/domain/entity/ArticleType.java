package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.feature.techinfo.application.generator.UpperCaseGenerator;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

public enum ArticleType {

    SOFTWARE_DEVELOPMENT_PROGRAMMING_LANGUAGE,
    SOFTWARE_DEVELOPMENT_WEB_DEVELOPMENT,
    SOFTWARE_DEVELOPMENT_MOBILE_DEVELOPMENT,
    SOFTWARE_DEVELOPMENT_GAME_DEVELOPMENT,
    SYSTEM_INFRA_DEVOPS,
    SYSTEM_INFRA_DATABASE,
    SYSTEM_INFRA_CLOUD,
    SYSTEM_INFRA_SECURITY_NETWORK,
    TECH_AI,
    TECH_DATA_SCIENCE,
    TECH_BLOCKCHAIN,
    TECH_VR_AR,
    TECH_HARDWARE,
    DESIGN_ART_UI_UX,
    DESIGN_ART_GRAPHICS,
    DESIGN_ART_MODELING_3D,
    DESIGN_ART_SOUND,
    BUSINESS_OFFICE,
    BUSINESS_PLANNING_PM,
    BUSINESS_AUTOMATION,
    BUSINESS_MARKETING,
    OTHER;

    public static ArticleType from(final String type) {
        final String normalizedType = UpperCaseGenerator.generate(type);
        validate(normalizedType);
        return valueOf(normalizedType);
    }

    public static void validate(final String type) {
        try {
            valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new RestApiException(CommonExceptionCode.ARTICLE_TYPE_NOT_FOUND);
        }
    }

}
