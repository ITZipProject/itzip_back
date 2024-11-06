package darkoverload.itzip.feature.algorithm.service.tag;

import darkoverload.itzip.feature.algorithm.controller.response.SolvedTagResponse;

public interface FindAllTagsService {
    SolvedTagResponse findSolvedTags(boolean recommended);
}
