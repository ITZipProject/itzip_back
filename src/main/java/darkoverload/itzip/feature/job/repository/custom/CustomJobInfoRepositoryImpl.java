package darkoverload.itzip.feature.job.repository.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.entity.QJobInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CustomJobInfoRepositoryImpl implements CustomJobInfoRepository{

    private final JPAQueryFactory queryFactory;
    private final QJobInfoEntity jobInfoEntity = QJobInfoEntity.jobInfoEntity;

    @Override
    public long bulkDeleteByPositionIds(List<Long> positionIds) {

        return queryFactory.delete(jobInfoEntity)
                .where(jobInfoEntity.positionId.in(positionIds))
                .execute();
    }

    @Override
    public Page<JobInfoSearchResponse> searchJobInfo(String search, String category, Integer experienceMin, Integer experienceMax, Pageable pageable) {

        BooleanExpression whereClause = createWhereClause(search, category, experienceMin, experienceMax);


        List<JobInfoSearchResponse> content = queryFactory.select(Projections.constructor(
                        JobInfoSearchResponse.class,
                        jobInfoEntity.id,
                        jobInfoEntity.title,
                        jobInfoEntity.industryName,
                        jobInfoEntity.locationName,
                        jobInfoEntity.jobName,
                        jobInfoEntity.expirationDate,
                        jobInfoEntity.experienceName,
                        jobInfoEntity.experienceMin,
                        jobInfoEntity.experienceMax
                ))
                .from(jobInfoEntity)
                .where(
                        whereClause
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(jobInfoEntity.count())
                .from(jobInfoEntity)
                .where(
                        whereClause
                ).fetchOne();

        // fetchOne()이 null을 반환할 수 있는 가능성을 대비해 0으로 초기화
        total = total != null ? total : 0L;

        return new PageImpl<>(content, pageable, total);
    }


    // 공통된 where 절을 생성하는 메서드
    private BooleanExpression createWhereClause(String search, String category, Integer experienceMin, Integer experienceMax) {
        return Expressions.allOf(
                searchEq(search),
                categoryEq(category),
                experienceMinGoe(experienceMin),
                experienceMaxLoe(experienceMax)
        );
    }


    // 검색어 조건
    private BooleanExpression searchEq(String search) {
        return hasText(search) ? jobInfoEntity.title.contains(search) : null;
    }

    // 기술명 조건
    private BooleanExpression categoryEq(String category) {
        return hasText(category) ? jobInfoEntity.jobName.contains(category) : null;
    }


    // 최소 경력 조건
    private BooleanExpression experienceMinGoe(Integer experienceMin) {
        return experienceMin != null ? jobInfoEntity.experienceMin.goe(experienceMin) : null;
    }


    // 최대 경력 조건
    private BooleanExpression experienceMaxLoe(Integer experienceMax) {
        return experienceMax != null ? jobInfoEntity.experienceMax.loe(experienceMax) : null;
    }

}
