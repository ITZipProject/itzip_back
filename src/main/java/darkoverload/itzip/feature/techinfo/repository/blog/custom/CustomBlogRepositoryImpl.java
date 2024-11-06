package darkoverload.itzip.feature.techinfo.repository.blog.custom;

import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;

import static darkoverload.itzip.feature.techinfo.model.entity.QBlogEntity.blogEntity;

@Repository
@RequiredArgsConstructor
public class CustomBlogRepositoryImpl implements CustomBlogRepository {

    private final JPAQueryFactory queryFactory;

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
