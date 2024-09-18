package darkoverload.itzip.feature.algorithm.service;

import darkoverload.itzip.feature.algorithm.service.problem.SaveProblems;
import darkoverload.itzip.feature.algorithm.service.tag.SaveTags;
import darkoverload.itzip.feature.algorithm.service.user.SaveUserSolvedProfile;
import darkoverload.itzip.feature.algorithm.service.user.UpdateUserSolvedProfileAndProblem;

public interface AlgorithmService extends
        SaveTags,
        SaveProblems,
        SaveUserSolvedProfile,
        UpdateUserSolvedProfileAndProblem
{}