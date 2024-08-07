package darkoverload.itzip.feature.school.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
                .limit(10)
                .where(schoolEntity.schoolName.like("%"+schoolName+"%"))
                .fetch();
    }


}
