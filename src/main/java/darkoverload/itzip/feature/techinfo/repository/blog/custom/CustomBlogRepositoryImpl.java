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

    // 블로그 소유자의 ID와 공개 여부를 기준으로 BlogEntity 조회
    @Override
    public Optional<BlogEntity> findByIdAndIsPublic(Long Id) {
        BlogEntity result = queryFactory
                .selectFrom(blogEntity)
                .where(blogEntity.userId.eq(Id)
                        .and(blogEntity.isPublic.isTrue()))  // 공개된 블로그만 조회
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
