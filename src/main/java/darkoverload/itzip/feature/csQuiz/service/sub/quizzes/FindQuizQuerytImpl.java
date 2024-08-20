package darkoverload.itzip.feature.csQuiz.service.sub.quizzes;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizQueryRequest;
import darkoverload.itzip.feature.csQuiz.entity.QuizUserSolvedMapping;
import darkoverload.itzip.feature.csQuiz.entity.SortBy;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizDocument;
import darkoverload.itzip.feature.csQuiz.repository.quiz.QuizRepository;
import darkoverload.itzip.feature.csQuiz.repository.quizusersolvedmapping.QuizUserSolvedMappingRepository;
import darkoverload.itzip.feature.csQuiz.util.QuizMapper;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//퀴즈 관련 로직을 구현하는 Impl
@Service
@RequiredArgsConstructor
public class FindQuizQuerytImpl implements FindQiuzQuery {
    //Quiz관련 리파지토리들
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final QuizUserSolvedMappingRepository quizUserSolvedMappingRepository;

    //User객체를 받아올 userRepository;
    private final UserRepository userRepository;

    // PageModel을 만들기위한 주입
    private final PagedResourcesAssembler<QuizDetailResponse> pagedResourcesAssembler;

    /**
     * 주어진 필터와 정렬 기준, 사용자 정보를 기반으로 퀴즈 목록을 조회하는 메서드
     *
     * @param quizQueryRequest 사용자가 보내주는 퀴즈 쿼리 객체
     * @return 필터링되고 정렬된 퀴즈 목록
     */
    @Override
    @Transactional
    public PagedModel<EntityModel<QuizDetailResponse>> findQuizzesByQuery(QuizQueryRequest quizQueryRequest) {
        // 페이지 정보와 정렬 기준을 기반으로 Pageable 객체 생성
        Pageable pageable = PageRequest.of(quizQueryRequest.getPage(), quizQueryRequest.getSize(), getSort(quizQueryRequest.getSortBy()));

        // 사용자가 푼 문제의 목록을 저장할 리스트 초기화
        List<QuizUserSolvedMapping> solvedProblemsEntity = List.of();
        // 사용자가 푼 문제의 Id값들만 저장할 리스트 초기화
        List<String> solvedProblemIds = new ArrayList<>();

        // 사용자가 있으면 사용자가 푼 문제를 가져옴
        if (quizQueryRequest.getUserId() != null) {
            //사용자를 찾고 사용자가 없을시 사용자가 없음 예외 출력
            UserEntity userEntity = userRepository.findById(quizQueryRequest.getUserId()).orElse(null);
            if (userEntity == null) {
                throw new RestApiException(CommonExceptionCode.NOT_FOUND_USER);
            }
            //사용자가 푼문제매핑 테이블 list로 받아옴
            solvedProblemsEntity = quizUserSolvedMappingRepository.findAllByUser(userEntity);
            // 사용자가 푼 문제의 problemId를 리스트로 추출
            solvedProblemIds = solvedProblemsEntity.stream()
                    .map(QuizUserSolvedMapping::getProblemId)
                    .toList();
        }
        //사용자가 푼문제들을 HashSet으로 만들어서 빠른 검색을 할수 있게함 key는 problemid값
        Set<QuizUserSolvedMapping> solvedProblemsSet = new HashSet<>(solvedProblemsEntity);

        //page된 quiz들을 받아옴
        Page<QuizDocument> quizzes = quizRepository.findByDifficultyAndCategoryAndUserSolved(
                quizQueryRequest.getDifficulty(),
                quizQueryRequest.getCategoryId(),
                solvedProblemIds,
                pageable,
                quizQueryRequest.isInUserSolved(),
                quizQueryRequest.getKeyword());

        //solved가 Ture면 사용자가 푼문제를 포함하니 푼문제인지 표시를 해줘야함 아닐경우 그냥 dto 변환
        List<QuizDetailResponse> quizDetailDtos = quizzes.getContent().stream()
                .map(quiz -> {
                    if (quizQueryRequest.isInUserSolved()) {
                        return quizMapper.documentsToResponse(quiz, solvedProblemsSet);
                    } else {
                        return quizMapper.documentsToResponse(quiz);
                    }
                })
                .toList();

        Page<QuizDetailResponse> quizDetailPage = new PageImpl<>(quizDetailDtos, pageable, quizzes.getTotalElements());

        // PagedModel 생성
        return pagedResourcesAssembler.toModel(quizDetailPage);
    }

    /**
     * 주어진 정렬 기준에 따라 정렬 객체를 생성하는 메서드
     *
     * @param sortBy 정렬 기준 (OLDEST 또는 NEWEST)
     * @return 주어진 정렬 기준에 따라 생성된 Sort 객체
     */
    private Sort getSort(SortBy sortBy) {
        switch (sortBy) {
            case OLDEST:
                // OLDEST 기준으로 정렬: 시간순 오름차순
                return Sort.by(Sort.Direction.ASC, "timeStamp");
            case NEWEST:
            default:
                // NEWEST 기준으로 정렬: 시간순 내림차순
                return Sort.by(Sort.Direction.DESC, "timeStamp");
        }
    }
}
