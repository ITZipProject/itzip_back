package darkoverload.itzip.feature.school.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static darkoverload.itzip.feature.school.entity.QSchoolEntity.schoolEntity;


@RequiredArgsConstructor
public class CustomSchoolRepositoryImpl implements CustomSchoolRepository {
    private final JPAQueryFactory queryFactory;

    public Long getTotalCount(){
        return queryFactory.select(schoolEntity.count())
                .from(schoolEntity).fetchOne();
    }

    @Override
    public List<String> searchBySchoolName(String schoolName) {
        return queryFactory.select(schoolEntity.schoolName)
                .from(schoolEntity)
                .where(schoolEntity.schoolName.like("%"+schoolName+"%"))
                .fetch();
    }
}
