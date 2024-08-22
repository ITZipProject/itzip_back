package darkoverload.itzip.feature.resume.service.achievement;

import darkoverload.itzip.feature.resume.repository.achievement.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;


    public void create() {

    }

}
