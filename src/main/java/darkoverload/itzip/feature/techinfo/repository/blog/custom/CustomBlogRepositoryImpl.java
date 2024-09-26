package darkoverload.itzip.feature.techinfo.repository.blog.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;

import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import static darkoverload.itzip.feature.techinfo.model.entity.QBlogEntity.blogEntity;

@Repository
@RequiredArgsConstructor
public class CustomBlogRepositoryImpl implements CustomBlogRepository {

    private final JPAQueryFactory queryFactory;

    // 블로그 ID와 공개 여부를 기준으로 BlogEntity 조회
    public Optional<BlogEntity> findByIdAndIsPublic(Long id) {
        BlogEntity result = queryFactory
                .selectFrom(blogEntity)
                .where(blogEntity.id.eq(id)
                        .and(blogEntity.isPublic.isTrue())) // 공개된 블로그만 조회
                .fetchOne();

        return Optional.ofNullable(result); // 결과를 Optional로 반환
    }

    // 사용자 ID와 공개 여부를 기준으로 BlogEntity 조회
    @Override
    public Optional<BlogEntity> findByUserIdAndIsPublic(Long userId) {
        BlogEntity result = queryFactory
                .selectFrom(blogEntity)
                .where(blogEntity.userId.eq(userId)
                        .and(blogEntity.isPublic.isTrue())) // 공개된 블로그만 조회
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
