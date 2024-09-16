package darkoverload.itzip.feature.algorithm.service;

import darkoverload.itzip.feature.algorithm.service.problem.SaveProblems;
import darkoverload.itzip.feature.algorithm.service.tag.SaveTags;
import darkoverload.itzip.feature.algorithm.service.user.SaveUserSolvedProblem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    private final SaveTags saveTags;
    private final SaveProblems saveProblems;
    private final SaveUserSolvedProblem saveUserSolvedProblem;

    public AlgorithmServiceImpl(
            @Qualifier("saveTagsImpl") SaveTags saveTags,
            @Qualifier("saveProblemsImpl") SaveProblems saveProblems,
            @Qualifier("saveUserSolvedProblemImpl") SaveUserSolvedProblem saveUserSolvedProblem
    ) {
        this.saveTags = saveTags;
        this.saveProblems = saveProblems;
        this.saveUserSolvedProblem = saveUserSolvedProblem;
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
    public void saveUserSolvedProblem(Long userId) {
        saveUserSolvedProblem.saveUserSolvedProblem(userId);
    }
}