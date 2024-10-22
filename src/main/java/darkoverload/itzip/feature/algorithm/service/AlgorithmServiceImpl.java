package darkoverload.itzip.feature.algorithm.service;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;
import darkoverload.itzip.feature.algorithm.controller.response.SolvedTagResponse;
import darkoverload.itzip.feature.algorithm.controller.response.SolvedUserResponse;
import darkoverload.itzip.feature.algorithm.service.problem.FindProblemsByTagAndUserService;
import darkoverload.itzip.feature.algorithm.service.problem.FindProblemsByUserService;
import darkoverload.itzip.feature.algorithm.service.problem.SaveProblemsService;
import darkoverload.itzip.feature.algorithm.service.tag.FindAllTagsService;
import darkoverload.itzip.feature.algorithm.service.tag.SaveTagsService;
import darkoverload.itzip.feature.algorithm.service.user.FindUserSolvedProfileService;
import darkoverload.itzip.feature.algorithm.service.user.SaveUserSolvedProfileService;
import darkoverload.itzip.feature.algorithm.service.user.UpdateUserSolvedProfileAndProblemService;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    private final SaveTagsService saveTagsService;
    private final SaveProblemsService saveProblemsService;
    private final SaveUserSolvedProfileService saveUserSolvedProfileService;
    private final UpdateUserSolvedProfileAndProblemService updateUserSolvedProfileAndProblemService;
    private final FindProblemsByUserService findProblemsByUserService;
    private final FindProblemsByTagAndUserService findProblemsByTagAndUserService;
    private final FindUserSolvedProfileService findUserSolvedProfileService;
    private final FindAllTagsService findAllTagsService;

    public AlgorithmServiceImpl(
            @Qualifier("saveTagsServiceImpl") SaveTagsService saveTagsService,
            @Qualifier("saveProblemsServiceImpl") SaveProblemsService saveProblemsService,
            @Qualifier("saveUserSolvedProfileServiceImpl") SaveUserSolvedProfileService saveUserSolvedProfileService,
            @Qualifier("updateUserSolvedProfileAndProblemServiceImpl") UpdateUserSolvedProfileAndProblemService updateUserSolvedProfileAndProblemService,
            @Qualifier("findProblemsByUserServiceImpl") FindProblemsByUserService findProblemsByUserService,
            @Qualifier("findProblemsByTagAndUserServiceImpl") FindProblemsByTagAndUserService findProblemsByTagAndUserService,
            @Qualifier("findUserSolvedProfileServiceImpl") FindUserSolvedProfileService findUserSolvedProfileService,
            @Qualifier("findAllTagsServiceImpl") FindAllTagsService findAllTagsService
            ) {
        this.saveTagsService = saveTagsService;
        this.saveProblemsService = saveProblemsService;
        this.saveUserSolvedProfileService = saveUserSolvedProfileService;
        this.updateUserSolvedProfileAndProblemService = updateUserSolvedProfileAndProblemService;
        this.findProblemsByUserService = findProblemsByUserService;
        this.findProblemsByTagAndUserService = findProblemsByTagAndUserService;
        this.findUserSolvedProfileService = findUserSolvedProfileService;
        this.findAllTagsService = findAllTagsService;
    }

    @Override
    public void saveProblemTags() {
        saveTagsService.saveProblemTags();
    }

    @Override
    public void saveProblems() {
        saveProblemsService.saveProblems();
    }

    @Override
    public void saveUserSolvedProfile(CustomUserDetails customUserDetails, String username) {
        saveUserSolvedProfileService.saveUserSolvedProfile(customUserDetails, username);
    }

    @Override
    public SolvedUserResponse updateUserSolvedProfileAndProblem(CustomUserDetails customUserDetails) {
        return updateUserSolvedProfileAndProblemService.updateUserSolvedProfileAndProblem(customUserDetails);
    }

    @Override
    public ProblemListResponse findProblemsByUser(CustomUserDetails customUserDetails) {
        return findProblemsByUserService.findProblemsByUser(customUserDetails);
    }

    @Override
    public ProblemListResponse findProblemsByTagAndUser(CustomUserDetails customUserDetails, Long tagId) {
        return findProblemsByTagAndUserService.findProblemsByTagAndUser(customUserDetails, tagId);
    }

    @Override
    public SolvedUserResponse findUserSolvedProfile(CustomUserDetails customUserDetails) {
        return findUserSolvedProfileService.findUserSolvedProfile(customUserDetails);
    }

    @Override
    public SolvedTagResponse findSolvedTags(boolean recommended) {
        return findAllTagsService.findSolvedTags(recommended);
    }
}