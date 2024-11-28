package darkoverload.itzip.feature.techinfo.repository.blog.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.model.entity.QBlogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 블로그 조회를 위한 커스텀 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class CustomBlogReadRepositoryImpl implements CustomBlogReadRepository {

    private final JPAQueryFactory queryFactory;
    private final QBlogEntity qBlog = QBlogEntity.blogEntity;

    /**
     * 블로그 ID로 공개된 블로그를 조회합니다.
     *
     * @param id 블로그 ID
     * @return Optional<BlogEntity>
     */
    @Override
    public Optional<BlogEntity> findByBlogId(Long id) {
        return findBlogByCondition(
                qBlog.id.eq(id)
                        .and(qBlog.isPublic.isTrue())
        );
    }

    /**
     * 사용자 ID로 공개된 블로그를 조회합니다.
     *
     * @param id 사용자 ID
     * @return Optional<BlogEntity>
     */
    @Override
    public Optional<BlogEntity> findByUserId(Long id) {
        return findBlogByCondition(
                qBlog.user.id.eq(id)
                        .and(qBlog.isPublic.isTrue())
        );
    }

    /**
     * 사용자 닉네임으로 공개된 블로그를 조회합니다.
     *
     * @param nickname 사용자 닉네임
     * @return Optional<BlogEntity>
     */
    @Override
    public Optional<BlogEntity> findByNickname(String nickname) {
        return findBlogByCondition(
                qBlog.user.nickname.eq(nickname)
                        .and(qBlog.isPublic.isTrue())
        );
    }

    /**
     * 주어진 조건에 맞는 블로그를 조회합니다.
     *
     * @param condition 조회 조건
     * @return Optional<BlogEntity>
     */
    private Optional<BlogEntity> findBlogByCondition(BooleanExpression condition) {
        BlogEntity result = queryFactory
                .selectFrom(qBlog)
                .where(condition)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
