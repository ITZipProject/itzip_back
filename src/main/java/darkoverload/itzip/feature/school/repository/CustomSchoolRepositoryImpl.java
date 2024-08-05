package darkoverload.itzip.feature.school.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static darkoverload.itzip.feature.school.entity.QSchoolEntity.schoolEntity;


@RequiredArgsConstructor
public class CustomSchoolRepositoryImpl implements CustomSchoolRepository{
    private final JPAQueryFactory queryFactory;

    public Long getTotalCount(){
        return queryFactory.select(schoolEntity.count())
                .from(schoolEntity).fetchOne();
    }
}
