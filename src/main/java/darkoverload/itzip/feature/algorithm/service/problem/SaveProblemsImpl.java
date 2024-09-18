package darkoverload.itzip.feature.algorithm.service.problem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.entity.ProblemTagEntity;
import darkoverload.itzip.feature.algorithm.entity.ProblemTagMappingEntity;
import darkoverload.itzip.feature.algorithm.entity.embedded.ProblemTagMappingId;
import darkoverload.itzip.feature.algorithm.repository.mapping.ProblemTagMappingRepository;
import darkoverload.itzip.feature.algorithm.repository.problem.ProblemRepository;
import darkoverload.itzip.feature.algorithm.util.SolvedAcAPI;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SaveProblemsImpl implements SaveProblems {
    private final SolvedAcAPI solvedAcAPI;
    private final ProblemRepository problemRepository;
    private final ProblemTagMappingRepository problemTagMappingRepository;

    /**
     * Solved.ac API를 통해 문제 데이터를 가져와서 데이터베이스에 저장하는 메서드
     * 각 페이지마다 문제 데이터를 가져오고, 문제와 태그 매핑을 처리하여 DB에 저장한다
     * API 호출은 페이지별로 한 번만 수행되며, 문제와 태그를 한 번에 처리한다
     * 1. Solved.ac API를 호출하여 문제 데이터를 가져옴
     * 2. 문제별로 태그 데이터를 파싱하고 매핑 엔티티를 생성함
     * 3. 문제 엔티티와 태그 매핑 엔티티를 한 번에 DB에 저장함
     * 예외 발생 시, RestApiException으로 변환하여 처리
     */
    @Override
    @Transactional
    public void saveProblems() {
        int start = 1;
        int batchSize = 50;

        List<ProblemEntity> problemsToSave = new ArrayList<>();
        List<ProblemTagMappingEntity> mappingsToSave = new ArrayList<>();
        try {
            // 총 문제 수 확인 후 페이지 계산
            HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getProblemByPage(1));
            int totalCount = JsonParser.parseString(response.body()).getAsJsonObject().get("count").getAsInt();
            int totalPages = totalCount / batchSize + 1;

            // 문제 데이터를 한 번에 처리
            for (int i = start; i <= totalPages; i++) {
                HttpResponse<String> problemsResponse = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getProblemByPage(i));
                JsonArray jsonProblems = JsonParser.parseString(problemsResponse.body()).getAsJsonArray();

                jsonProblems.forEach(problemElement -> {
                    JsonObject jsonProblem = problemElement.getAsJsonObject();

                    // 문제 제목 처리
                    String problemTitle = StreamSupport.stream(jsonProblem.getAsJsonArray("titles").spliterator(), false)
                            .map(JsonObject.class::cast)
                            .filter(title -> title.get("language").getAsString().equals("ko"))
                            .findFirst()
                            .orElse(jsonProblem.getAsJsonArray("titles").get(0).getAsJsonObject())
                            .get("title").getAsString();

                    // ProblemEntity 생성
                    ProblemEntity problemEntity = ProblemEntity.builder()
                            .problemId(jsonProblem.get("problemId").getAsLong())
                            .level(jsonProblem.get("level").getAsInt())
                            .acceptedUserCount(jsonProblem.get("acceptedUserCount").getAsLong())
                            .title(problemTitle)
                            .averageTries(jsonProblem.get("averageTries").getAsInt())
                            .build();

                    problemsToSave.add(problemEntity);

                    // 태그 처리
                    JsonArray tags = jsonProblem.getAsJsonArray("tags");
                    tags.forEach(tagElement -> {
                        JsonObject tag = tagElement.getAsJsonObject();
                        Long tagId = tag.get("bojTagId").getAsLong();

                        // 매핑 생성
                        ProblemTagMappingEntity mapping = ProblemTagMappingEntity.builder()
                                .problemTagMappingId(ProblemTagMappingId.builder()
                                        .problemTagId(tagId)
                                        .problemId(problemEntity.getProblemId())
                                        .build())
                                .problemTagEntity(ProblemTagEntity.builder()
                                        .bojTagId(tagId)
                                        .build())
                                .problemEntity(problemEntity)
                                .build();

                        mappingsToSave.add(mapping);
                    });
                });
            }
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.SOLVED_PROBLEM_ERROR);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestApiException(CommonExceptionCode.SOLVED_PROBLEM_ERROR);
        }

        // 문제와 태그 매핑을 한 번에 저장
        problemRepository.saveAll(problemsToSave);
        problemTagMappingRepository.saveAll(mappingsToSave);
    }
}