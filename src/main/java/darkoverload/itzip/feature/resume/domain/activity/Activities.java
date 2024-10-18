package darkoverload.itzip.feature.resume.domain.activity;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.activity.ActivityDto;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class Activities {
    private final List<Activity> activities;

    public Activities(List<Activity> activities) {
        this.activities = activities;
    }

    public static Optional<Activities> of(List<ActivityDto> activities, Resume resume) {
        if (isValidate(activities)) {
            return Optional.of(new Activities(parse(activities, resume)));
        }

        return Optional.empty();
    }

    public static List<Activity> parse(List<ActivityDto> activities, Resume resume) {
        return activities.stream()
                .map(createActivityDto -> {
                    Activity activity = createActivityDto.create();
                    activity.setResume(resume);
                    return activity;
                }).toList();
    }

    public static boolean isValidate(List<ActivityDto> activities) {
        return !activities.isEmpty();
    }
}
