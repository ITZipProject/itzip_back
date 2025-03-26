package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.feature.user.entity.UserEntity;

import java.time.LocalDateTime;

public class UserFixture {

    private UserFixture() {
    }

    public static final Long DEFAULT_ID = 999L;
    public static final Long SECOND_ID = 1000L;
    public static final Long NON_EXISTENT_ID = -1L;

    public static final String DEFAULT_EMAIL = "dev.hyoseung@gmail.com";
    public static final String SECOND_EMAIL = "20181189@vision.hoseo.edu";
    public static final String NON_EXISTENT_EMAIL = "hoohoot0225@gmail.com";

    public static final String DEFAULT_NICKNAME = "hyoseung";
    public static final String SECOND_NICKNAME = "rowing";
    public static final String NON_EXISTENT_NICKNAME = "hoohoot0225";

    public static final String DEFAULT_PASSWORD = "password";
    public static final String DEFAULT_PROFILE_IMAGE_URI = "";
    public static final Authority DEFAULT_AUTHORITY = Authority.USER;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2025, 3, 4, 0, 0, 0);
    public static final String DEFAULT_SNS_TYPE = "";

    public static UserEntity getUser() {
        return new UserEntity(DEFAULT_EMAIL, DEFAULT_NICKNAME, DEFAULT_PASSWORD, DEFAULT_PROFILE_IMAGE_URI, DEFAULT_AUTHORITY, DEFAULT_DATE_TIME, DEFAULT_DATE_TIME);
    }

}
