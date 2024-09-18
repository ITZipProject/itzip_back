package darkoverload.itzip.feature.algorithm.service;

import darkoverload.itzip.feature.algorithm.service.problem.SaveProblems;
import darkoverload.itzip.feature.algorithm.service.tag.SaveTags;
import darkoverload.itzip.feature.algorithm.service.user.SaveUserSolvedProfile;
import darkoverload.itzip.feature.algorithm.service.user.UpdateUserSolvedProfileAndProblem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    private final SaveTags saveTags;
    private final SaveProblems saveProblems;
    private final SaveUserSolvedProfile saveUserSolvedProfile;
    private final UpdateUserSolvedProfileAndProblem updateUserSolvedProfileAndProblem;

    public AlgorithmServiceImpl(
            @Qualifier("saveTagsImpl") SaveTags saveTags,
            @Qualifier("saveProblemsImpl") SaveProblems saveProblems,
            @Qualifier("saveUserSolvedProfileImpl") SaveUserSolvedProfile saveUserSolvedProfile,
            @Qualifier("updateUserSolvedProfileAndProblemImpl") UpdateUserSolvedProfileAndProblem updateUserSolvedProfileAndProblem
    ) {
        this.saveTags = saveTags;
        this.saveProblems = saveProblems;
        this.saveUserSolvedProfile = saveUserSolvedProfile;
        this.updateUserSolvedProfileAndProblem = updateUserSolvedProfileAndProblem;
    }

    @Override
    public void saveProblemTags() {
        saveTags.saveProblemTags();
    }

    @Override
    public void saveProblems() {
        saveProblems.saveProblems();
    }

    @Override
    public void saveUserSolvedProfile(Long userId, String username) {
        saveUserSolvedProfile.saveUserSolvedProfile(userId, username);
    }

    @Override
    public void updateUserSolvedProfileAndProblem(Long userId) {
        updateUserSolvedProfileAndProblem.updateUserSolvedProfileAndProblem(userId);
    }
}