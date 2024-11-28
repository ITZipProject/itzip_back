package darkoverload.itzip.feature.job.repository.custom;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.domain.QJobInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;
@Repository
@RequiredArgsConstructor
public class CustomJobInfoRepositoryImpl implements CustomJobInfoRepository{

    private final JPAQueryFactory queryFactory;
    private final QJobInfo jobInfoEntity = QJobInfo.jobInfo;

    @Override
    public long bulkDeleteByPositionIds(List<Long> positionIds) {

        return queryFactory.delete(jobInfoEntity)
                .where(jobInfoEntity.positionId.in(positionIds))
                .execute();
    }

    /**
     * 주어진 검색 조건에 따라 채용 정보를 검색하고 페이징 처리된 결과를 반환하는 메서드.
     *
     * 1. 검색 조건 (검색어, 카테고리, 최소 및 최대 경력)에 따라 조건을 생성합니다.
     * 2. 정렬 조건을 적용하여 결과를 정렬합니다.
     * 3. 페이징 처리를 통해 페이지 번호, 페이지 크기 및 정렬 조건을 반영하여 데이터를 가져옵니다.
     * 4. 페이징된 채용 정보 목록과 전체 레코드 수를 반환합니다.
     *
     * @param search String: 검색어 (채용 정보의 제목 또는 기타 필드를 기준으로 검색)
     * @param category String: 카테고리 (해당 카테고리에 속하는 채용 정보를 필터링)
     * @param experienceMin Integer: 최소 경력 (해당 값 이상 경력을 가진 채용 정보를 필터링)
     * @param experienceMax Integer: 최대 경력 (해당 값 이하 경력을 가진 채용 정보를 필터링)
     * @param pageable Pageable: 페이지 정보와 정렬 조건을 포함한 페이징 처리 정보 (페이지 번호, 페이지 크기, 정렬 조건)
     * @return Page<JobInfoSearchResponse>: 페이징된 채용 정보 목록을 반환
     */
    @Override
    public Page<JobInfoSearchResponse> searchJobInfo(String search, String category, Integer experienceMin, Integer experienceMax, Pageable pageable) {
        // 검색 조건에 따라 동적으로 Where 절 생성
        BooleanExpression whereClause = createWhereClause(search, category, experienceMin, experienceMax);

        // Pageable에서 정렬 조건을 추출하여 적용
        OrderSpecifier<?>[] sortOrder = createSortOrder(pageable);

        // 주어진 조건에 따라 채용 정보를 조회하고 페이징 처리
        List<JobInfoSearchResponse> content = queryFactory.select(Projections.constructor(
                        JobInfoSearchResponse.class,
                        jobInfoEntity.id,
                        jobInfoEntity.title,
                        jobInfoEntity.industryName,
                        jobInfoEntity.locationName,
                        jobInfoEntity.locationCode,
                        jobInfoEntity.jobName,
                        jobInfoEntity.expirationDate,
                        jobInfoEntity.experienceName,
                        jobInfoEntity.experienceMin,
                        jobInfoEntity.experienceMax,
                        jobInfoEntity.scrapCount,
                        jobInfoEntity.companyName,
                        jobInfoEntity.url
                ))
                .from(jobInfoEntity)
                .where(
                        whereClause
                )
                .orderBy(sortOrder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 레코드 수를 계산
        Long total = queryFactory.select(jobInfoEntity.count())
                .from(jobInfoEntity)
                .where(
                        whereClause
                ).fetchOne();

        // fetchOne()이 null을 반환할 수 있는 가능성을 대비해 0으로 초기화
        total = total != null ? total : 0L;

        // 조회된 채용 정보와 전체 레코드 수를 기반으로 페이징 결과 반환
        return new PageImpl<>(content, pageable, total);
    }


    /**
     * 주어진 검색 조건을 기반으로 동적으로 WHERE 절을 생성하는 메서드.
     *
     * 1. 검색어, 카테고리, 최소 경력, 최대 경력 조건을 동적으로 결합하여 WHERE 절을 구성합니다.
     * 2. 각각의 조건이 `null`일 수 있기 때문에, 조건이 존재하는 경우에만 해당 조건을 추가합니다.
     * 3. 여러 조건을 조합하기 위해 QueryDSL의 `Expressions.allOf()`를 사용합니다.
     *
     * @param search String: 검색어 (null 가능, 특정 키워드로 검색할 때 사용)
     * @param category String: 카테고리 (null 가능, 특정 카테고리로 필터링할 때 사용)
     * @param experienceMin Integer: 최소 경력 (null 가능, 이 값 이상 경력의 채용 정보를 필터링)
     * @param experienceMax Integer: 최대 경력 (null 가능, 이 값 이하 경력의 채용 정보를 필터링)
     * @return BooleanExpression: 동적으로 생성된 검색 조건을 포함한 WHERE 절
     */
    private BooleanExpression createWhereClause(String search, String category, Integer experienceMin, Integer experienceMax) {
        return Expressions.allOf(
                searchEq(search),
                categoryEq(category),
                experienceMinGoe(experienceMin),
                experienceMaxLoe(experienceMax)
        );
    }

    /**
     * Pageable 객체로부터 정렬 정보를 추출하여 QueryDSL의 OrderSpecifier 배열을 생성하는 메서드.
     *
     * 1. Pageable 객체에 포함된 Sort 정보를 기반으로 정렬 필드를 추출합니다.
     * 2. 각 필드에 대해 오름차순 또는 내림차순 정렬을 적용합니다.
     * 3. 필드명이 "modifyDate" 또는 "scrap"인 경우에만 정렬 조건을 설정하며,
     *    그 외 필드는 처리하지 않고 null을 반환합니다.
     * 4. null 값을 필터링하여 유효한 정렬 조건만 배열로 반환합니다.
     *
     * @param pageable Pageable: 페이지 정보와 정렬 조건을 포함하는 객체
     * @return OrderSpecifier<?>[]: QueryDSL에서 사용할 수 있는 정렬 조건 배열
     */
    private OrderSpecifier<?>[] createSortOrder(Pageable pageable) {
        // Pageable에서 정렬 정보를 가져와 처리
        return pageable.getSort().stream()
                .map(order -> {
                    // 필드명에 따라 정렬 기준을 다르게 설정
                    switch (order.getProperty()) {
                        case "modifyDate":
                            return order.isAscending() ? jobInfoEntity.modifyDate.asc() : jobInfoEntity.modifyDate.desc();
                        case "scrap":
                            return order.isAscending() ? jobInfoEntity.scrapCount.asc() : jobInfoEntity.scrapCount.desc();
                        // 필요한 필드별로 추가적인 정렬 조건을 설정할 수 있음
                        default:
                            return null;  // 알 수 없는 필드의 경우 null 반환
                    }
                })
                .filter(Objects::nonNull) // null 값 필터링
                .toArray(OrderSpecifier[]::new); // 배열로 변환하여 반환
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
