package darkoverload.itzip.feature.algorithm.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.entity.UserProblemMappingEntity;
import darkoverload.itzip.feature.algorithm.entity.embedded.UserProblemMappingId;
import darkoverload.itzip.feature.algorithm.repository.mapping.UserProblemMappingRepository;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveUserSolvedProblem {
    private final SolvedAcAPI solvedAcAPI;

    private final SolvedacUserRepository solvedacUserRepository;
    private final UserProblemMappingRepository userProblemMappingRepository;

    /**
     * 사용자가 푼 문제들을 Solved.ac API를 통해 가져와서 매핑 테이블에 저장하는 메서드
     *
     * @param userId Solved.ac 사용자 ID
     */
    public void saveUserSolvedProblem(Long userId) {
        SolvedacUser solvedacUser = solvedacUserRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER))
                .convertToDomain();

        int start = 1;
        int batchSize = 50;

        List<UserProblemMappingEntity> mappingToSave = new ArrayList<>();

        try{
            HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getUserSolvedProblemByName(solvedacUser.getUsername(), 1));
            int totalCount = JsonParser.parseString(response.body()).getAsJsonObject().get("count").getAsInt();
            int totalPages = totalCount / batchSize + 1;

            // 각 페이지에 해당하는 문제 데이터를 Solved.ac API로부터 가져옴
            for (int i = start; i <= totalPages; i++) {
                HttpResponse<String> problemsResponse = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getUserSolvedProblemByName(solvedacUser.getUsername(), i));
                JsonObject jsonObject = JsonParser.parseString(problemsResponse.body()).getAsJsonObject();
                JsonArray jsonProblems = jsonObject.getAsJsonArray("items");

                jsonProblems.forEach(problemElement -> {
                    JsonObject jsonProblem = problemElement.getAsJsonObject();

                    // 문제 매핑 엔티티 생성
                    mappingToSave.add(UserProblemMappingEntity.builder()
                            .userProblemMappingId(UserProblemMappingId.builder()
                                    .userId(userId)
                                    .problemId(jsonProblem.get("problemId").getAsLong())
                                    .build())
                            .problemEntity(ProblemEntity.builder()
                                    .problemId(jsonProblem.get("problemId").getAsLong())
                                    .build())
                            .solvedacUserEntity(SolvedacUserEntity.builder()
                                    .userId(userId)
                                    .build())
                            .build());
                });
            }
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.SOLVED_USER_SOLVED_PROBLEM_ERROR);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestApiException(CommonExceptionCode.SOLVED_USER_SOLVED_PROBLEM_ERROR);
        }

        // 매핑된 사용자-문제 데이터를 DB에 저장
        userProblemMappingRepository.saveAll(mappingToSave);
    }
}
