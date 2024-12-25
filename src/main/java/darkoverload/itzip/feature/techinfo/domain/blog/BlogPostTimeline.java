package darkoverload.itzip.feature.techinfo.domain.blog;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

/**
 * 블로그의 포스트 타임라인을 나타내는 도메인 클래스.
 * 블로그 소유자의 닉네임과 포스트 목록을 포함합니다.
 */
@Getter
public class BlogPostTimeline {

    private final String nickname;
    private final List<Post> posts;

    @Builder
    public BlogPostTimeline(String nickname, List<Post> posts) {
        this.nickname = nickname;
        this.posts = posts;
    }

    /**
     * 주어진 닉네임과 포스트 목록으로 BlogPostTimeline 생성합니다.
     *
     * @param nickname 블로그 소유자의 닉네임
     * @param posts    포스트 목록
     * @return BlogPostTimeline
     */
    public static BlogPostTimeline from(String nickname, List<Post> posts) {
        return BlogPostTimeline.builder()
                .nickname(nickname)
                .posts(posts)
                .build();
    }

}
