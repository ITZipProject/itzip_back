package darkoverload.itzip.feature.csQuiz.service.Impl.quizzes;

import darkoverload.itzip.feature.csQuiz.entity.SortBy;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizEntity;
import darkoverload.itzip.feature.csQuiz.repository.quiz.QuizRepository;
import darkoverload.itzip.feature.csQuiz.repository.quizusersolvedmapping.QuizUserSolvedMappingRepository;
import darkoverload.itzip.feature.csQuiz.util.QuizMapper;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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

    /**
     * 주어진 필터와 정렬 기준, 사용자 정보를 기반으로 퀴즈 목록을 조회하는 메서드
     *
     * @param difficulty 퀴즈 난이도 (선택 사항)
     * @param categoryId 카테고리 ID (선택 사항)
     * @param sortBy 정렬 기준 (OLDEST 또는 NEWEST)
     * @param userId 사용자 ID (0이면 모든 사용자)
     * @param solved 사용자가 푼 문제만 조회할지 여부
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 필터링되고 정렬된 퀴즈 목록
     */
    @Override
    public Page<QuizDetailResponse> QuizzesByDifficultyAndCategoryIdAndUserId(
            Integer difficulty, Long categoryId,
            SortBy sortBy, Long userId, boolean solved, int page, int size) {
        // 페이지 정보와 정렬 기준을 기반으로 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, getSort(sortBy));

        // 사용자가 푼 문제의 ID 목록을 저장할 리스트 초기화
        List<String> solvedProblemIds = new ArrayList<>();

        if (userId != 0){
            //사용자를 찾고 사용자가 없을시 사용자가 없음 예외 출력
            UserEntity userEntity = userRepository.findById(userId).orElse(null);
            if (userEntity == null) {
                throw new RestApiException(CommonExceptionCode.NOT_FOUND_USER);
            }
            //사용자가 푼문의 id값들을 list로 받아옴
            solvedProblemIds = quizUserSolvedMappingRepository.findProblemIdsByUser(userEntity);
        }

        Page<QuizEntity> quizzes;
        List<QuizDetailResponse> quizDetailDtos;

        if (solved) {
            //사용자가 풀지 않은 문제를 가져옴
            quizzes = quizRepository.findByDifficultyAndCategoryAndUserSolved(difficulty, categoryId, solvedProblemIds, pageable);//
            quizDetailDtos = quizzes.getContent().stream()
                    .map(quizMapper::entitiestoResponse)
                    .toList();
        } else {
            //사용자가 풀었는지에 관련이 없는 문제를 가져옴
            quizzes = quizRepository.findByDifficultyAndCategory(difficulty, categoryId, pageable);
            //사용자가 푼문제라면 푼 문제라는 것을 표시함
            Set<String> setSovledProblemIds = new HashSet<>(solvedProblemIds);
            quizDetailDtos = quizzes.getContent().stream()
                    .map(quiz -> quizMapper.entitiestoResponse(quiz, setSovledProblemIds))
                    .toList();
        }

        return new PageImpl<>(quizDetailDtos, pageable, quizzes.getTotalElements());
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
