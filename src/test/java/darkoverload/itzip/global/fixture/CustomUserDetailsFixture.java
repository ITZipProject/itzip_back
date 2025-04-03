package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

import java.util.List;

/**
 * 테스트용 CustomUserDetails 객체를 제공하는 Fixture 클래스입니다.
 *
 * <p>
 *     사용자 인증 정보 관련 테스트에서 사용될 CustomUserDetails 객체를 생성하며 반환합니다.
 * </p>
 */
public class CustomUserDetailsFixture {

    private CustomUserDetailsFixture() {
    }

    public static CustomUserDetails getCustomUserDetails() {
        return new CustomUserDetails(UserFixture.DEFAULT_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.DEFAULT_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
    }

}
