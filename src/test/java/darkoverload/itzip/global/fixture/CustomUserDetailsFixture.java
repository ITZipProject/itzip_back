package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.entity.Authority;

import java.util.List;

public class CustomUserDetailsFixture {

    private CustomUserDetailsFixture() {
    }

    public static CustomUserDetails getCustomUserDetails() {
        return new CustomUserDetails(UserFixture.DEFAULT_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.DEFAULT_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
    }

}
