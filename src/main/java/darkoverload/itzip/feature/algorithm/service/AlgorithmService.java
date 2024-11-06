package darkoverload.itzip.feature.algorithm.service;

import darkoverload.itzip.feature.algorithm.service.problem.FindProblemsByTagAndUserService;
import darkoverload.itzip.feature.algorithm.service.problem.FindProblemsByUserService;
import darkoverload.itzip.feature.algorithm.service.problem.SaveProblemsService;
import darkoverload.itzip.feature.algorithm.service.tag.FindAllTagsService;
import darkoverload.itzip.feature.algorithm.service.tag.SaveTagsService;
import darkoverload.itzip.feature.algorithm.service.user.FindUserSolvedProfileService;
import darkoverload.itzip.feature.algorithm.service.user.SaveUserSolvedProfileService;
import darkoverload.itzip.feature.algorithm.service.user.UpdateUserSolvedProfileAndProblemService;

public interface AlgorithmService extends
        SaveTagsService,
        SaveProblemsService,
        SaveUserSolvedProfileService,
        UpdateUserSolvedProfileAndProblemService,
        FindProblemsByUserService,
        FindProblemsByTagAndUserService,
        FindUserSolvedProfileService,
        FindAllTagsService
{}