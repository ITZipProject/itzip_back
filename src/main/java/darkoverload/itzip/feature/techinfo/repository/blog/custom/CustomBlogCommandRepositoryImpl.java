package darkoverload.itzip.feature.techinfo.repository.blog.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.techinfo.model.entity.QBlogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 블로그 정보 수정을 위한 커스텀 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class CustomBlogCommandRepositoryImpl implements CustomBlogCommandRepository {

    private final JPAQueryFactory queryFactory;

    private final QBlogEntity qBlog = QBlogEntity.blogEntity;

    /**
     * 사용자의 블로그 소개글을 업데이트합니다.
     *
     * @param userId   사용자 ID
     * @param newIntro 새로운 소개글
     * @return 업데이트된 블로그 수
     */
    @Override
    public long update(Long userId, String newIntro) {
        return queryFactory
                .update(qBlog)
                .set(qBlog.intro, newIntro)
                .where(
                        qBlog.user.id.eq(userId)
                                .and(qBlog.isPublic.isTrue())
                )
                .execute();
    }

    /**
     * 블로그의 공개 상태를 업데이트합니다.
     *
     * @param blogId 블로그 ID
     * @param status 새로운 공개 상태
     * @return 업데이트된 블로그 수
     */
    @Override
    public long update(Long blogId, boolean status) {
        return queryFactory
                .update(qBlog)
                .set(qBlog.isPublic, status)
                .where(
                        qBlog.id.eq(blogId)
                                .and(qBlog.isPublic.isTrue())
                )
                .execute();
    }

}
