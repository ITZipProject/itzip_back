package darkoverload.itzip.feature.resume.repository.resume.custom;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.resume.QResumeEntity;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomResumeReadRepositoryImpl implements CustomResumeReadRepository{

    private final JPAQueryFactory queryFactory;

    private QResumeEntity qResume = QResumeEntity.resumeEntity;

    @Override
    public List<ResumeEntity> searchResumeInfos(String search, Pageable pageable) {
        OrderSpecifier<?>[] sortOrder = sortResumeSpecifier(pageable);
        BooleanExpression expression = Expressions.allOf(searchEq(search),
                qResume.basicInfo.publicOnOff.eq(PublicOnOff.YES));


        return queryFactory.selectFrom(qResume)
                .where(expression)
                .orderBy(sortOrder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private OrderSpecifier[] sortResumeSpecifier(Pageable pageable) {
        return pageable.getSort().stream()
                .map(order -> {
                    switch (order.getProperty()) {
                        case "modifyDate":
                            return order.isAscending() ? qResume.modifyDate.asc() : qResume.modifyDate.desc();
                        default:
                            return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(OrderSpecifier[]::new);
    }

    private BooleanExpression searchEq(String search) {
        return hasText(search) ? qResume.basicInfo.subject.contains(search) : null;
    }

}
