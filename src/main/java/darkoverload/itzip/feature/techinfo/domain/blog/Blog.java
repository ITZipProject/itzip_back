package darkoverload.itzip.feature.techinfo.domain.blog;

import darkoverload.itzip.feature.user.domain.User;
import lombok.Builder;
import lombok.Getter;

/**
 * 기술 정보 블로그를 나타내는 도메인 클래스.
 * 블로그의 ID, 소유자, 소개글, 공개 여부 정보를 포함합니다.
 */
@Getter
public class Blog {

    private final Long id;
    private final User user;
    private final String intro;
    private final boolean isPublic;

    @Builder
    public Blog(Long id, User user, String intro, Boolean isPublic) {
        this.id = id;
        this.user = user;
        this.intro = intro;
        this.isPublic = isPublic;
    }

    /**
     * 주어진 사용자로 새 블로그를 생성합니다.
     * 생성된 블로그는 기본적으로 공개 상태입니다.
     *
     * @param user 블로그 소유자
     * @return Blog
     */
    public static Blog from(User user) {
        return Blog.builder()
                .user(user)
                .isPublic(true)
                .build();
    }

}
