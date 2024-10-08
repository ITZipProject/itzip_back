package darkoverload.itzip.feature.algorithm.service.tag;

import darkoverload.itzip.feature.algorithm.controller.response.SolvedTagResponse;
import darkoverload.itzip.feature.algorithm.domain.ProblemTag;
import darkoverload.itzip.feature.algorithm.entity.ProblemTagEntity;
import darkoverload.itzip.feature.algorithm.repository.tag.ProblemTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//모든 tag를 넘겨주는 서비스 로직
@Service
@RequiredArgsConstructor
public class FindAllTagsServiceImpl implements FindAllTagsService {
    private final ProblemTagRepository problemTagRepository;

    //프로그래머스 고득점 키트를 기준으로했음
    //너비, 깊이, 이분탐색, 그리디, 그래프탐색, 다이나믹, 시뮬레이션, 백트래킹, 우선순위, 트리, 정렬, 스택, 큐, 자료구조
    private final List<Long> recommendedTags = new ArrayList<>(Stream.of(
            126, 127, 12, 33, 11, 25, 59, 141, 5, 120, 97, 71, 72 ,175
    ).map(Integer::longValue).toList());

    /**
     * 모든 tag의 정보를 넘겨준다.
     * @return tag들을 담은 객체
     */
    @Override
    public SolvedTagResponse findSolvedTags(boolean recommended) {
        List<ProblemTag> tags = findTags(recommended);

        return SolvedTagResponse.builder()
                .tags(tags)
                .build();
    }

    /**
     * recommended에 따라서 tag를 찾는 메서드
     * @param recommended ture일 경우 추천된 것만 아닐경우 전부
     * @return tag 리스트 목록
     */
    private List<ProblemTag> findTags(boolean recommended) {
        if (recommended) {
            return problemTagRepository.findAllById(recommendedTags).stream()
                    .map(ProblemTagEntity::convertToDomain)
                    .toList();
        }
        return problemTagRepository.findAll().stream()
                .map(ProblemTagEntity::convertToDomain)
                .toList();
    }
}