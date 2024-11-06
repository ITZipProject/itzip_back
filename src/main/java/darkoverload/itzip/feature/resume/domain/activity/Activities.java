package darkoverload.itzip.feature.resume.domain.activity;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.activity.ActivityDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class Activities {
    private final List<Activity> activities;

    public Activities() {
        this(new ArrayList<>());
    }

    public Activities(List<Activity> activities) {
        this.activities = activities;
    }

    public static Optional<Activities> of(List<ActivityDto> activities, Resume resume) {
        if (activities != null && isValidate(activities)) {
            return Optional.of(new Activities(parse(activities, resume)));
        }

        return Optional.empty();
    }

    public static Optional<Activities> of(List<Activity> activities) {
        return Optional.of(new Activities(activities));
    }

    public static List<Activity> parse(List<ActivityDto> activities, Resume resume) {
        return activities.stream()
                .map(activityDto -> {
                    Activity activity = activityDto.toModel();
                    activity.setResume(resume);
                    return activity;
                }).toList();
    }

    public static boolean isValidate(List<ActivityDto> activities) {
        return !activities.isEmpty();
    }

    public List<Activity> deleteActivities(List<Activity> allActivities) {
        return activities.stream().filter(activity -> {
            return !allActivities.contains(activity);
        }).toList();
    }

}
