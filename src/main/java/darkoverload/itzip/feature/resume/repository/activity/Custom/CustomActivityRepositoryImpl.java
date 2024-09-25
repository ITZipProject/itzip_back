package darkoverload.itzip.feature.resume.repository.activity.Custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import darkoverload.itzip.feature.resume.entity.QActivityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CustomActivityRepositoryImpl implements CustomActivityRepository{

    private final JPAQueryFactory queryFactory;
    private final QActivityEntity qActivity = QActivityEntity.activityEntity;

    @Override
    public List<ActivityEntity> findAllByResumeId(Long resumeId) {

        return queryFactory.selectFrom(qActivity)
                .where(qActivity.resume.id.eq(resumeId))
                .fetch();
    }
}
