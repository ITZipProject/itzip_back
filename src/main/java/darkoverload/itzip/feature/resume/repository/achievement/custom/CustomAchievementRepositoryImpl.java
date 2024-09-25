package darkoverload.itzip.feature.resume.repository.achievement.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import darkoverload.itzip.feature.resume.entity.QAchievementEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomAchievementRepositoryImpl implements CustomAchievementRepository{

    private final JPAQueryFactory queryFactory;

    private final QAchievementEntity qAchievement = QAchievementEntity.achievementEntity;

    public List<AchievementEntity> findAllByResumeId(Long resumeId) {

        return queryFactory.selectFrom(qAchievement)
                .where(qAchievement.resume.id.eq(resumeId)).fetch();

    }
}
