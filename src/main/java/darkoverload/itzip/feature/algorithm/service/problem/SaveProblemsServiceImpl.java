package darkoverload.itzip.feature.algorithm.service.problem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class SaveProblemsServiceImpl implements SaveProblemsService {
    private final SolvedAcAPI solvedAcAPI;
    private final ProblemRepository problemRepository;
    private final ProblemTagMappingRepository problemTagMappingRepository;

    private final List<Object[]> problemsToSave = new ArrayList<>();
    private final List<Object[]> mappingsToSave = new ArrayList<>();

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
        try {
            // 총 문제 수 확인 후 페이지 계산
            HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getProblemByPage(1));
            int totalCount = JsonParser.parseString(response.body()).getAsJsonObject().get("count").getAsInt();
            int batchSize = 50;
            int totalPages = totalCount / batchSize + 1;

            processPage(totalPages);
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.SOLVED_PROBLEM_ERROR);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestApiException(CommonExceptionCode.SOLVED_PROBLEM_ERROR);
        }

        // 문제와 태그 매핑을 한 번에 저장
        problemRepository.batchInsertProblems(problemsToSave);
        problemTagMappingRepository.batchInsertProblemsAndTagsMapping(mappingsToSave);
    }

    /**
     * solved api에 모든 문제 페이지를 호출해주는 메서드
     * items에 있는 값들을 JsonArray로 변환하는 작업을 한다.
     * @param totalPages 총 페이지
     */
    private void processPage(int totalPages) throws IOException, InterruptedException {
        // 문제 데이터를 한 번에 처리
        int start = 1;
        for (int i = start; i <= totalPages; i++) {
            HttpResponse<String> problemsResponse = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getProblemByPage(i));
            JsonObject jsonObject = JsonParser.parseString(problemsResponse.body()).getAsJsonObject();
            JsonArray jsonProblems = jsonObject.getAsJsonArray("items");

            processProblem(jsonProblems);
        }
    }

    /**
     * 각각에 문제들의 값을 꺼내는 메서드
     * @param jsonProblems 문제 한개를 받는다.
     */
    private void processProblem(JsonArray jsonProblems) {
        if (jsonProblems != null && !jsonProblems.isEmpty()) {
            jsonProblems.forEach(problemElement -> {
                JsonObject jsonProblem = problemElement.getAsJsonObject();
                long problemId = jsonProblem.get("problemId").getAsLong();

                processProblemTitle(jsonProblem, problemId);

                processProblemTags(jsonProblem, problemId);
            });
        }
    }

    /**
     * 문제에 있는 테그를 처리하는 메서드 문제랑 tag를 매핑한다.
     * @param jsonProblem 문제
     * @param problemId 문제 id값
     */
    private void processProblemTags(JsonObject jsonProblem, long problemId) {
        JsonArray tags = jsonProblem.getAsJsonArray("tags");
        if (!tags.isEmpty()) {
            tags.forEach(tagElement -> {
                JsonObject tag = tagElement.getAsJsonObject();
                long boj_tag_id = tag.get("bojTagId").getAsLong();
                mappingsToSave.add(new Object[]{
                        problemId, boj_tag_id
                });
            });
        }
    }

    /**
     * 문제의 제목을 처리하는 메서드
     * 문제에는 한글, 미국어, 중어등 여러 언어가 있으며 한글을 우선으로 하되 한글이 없다면 배열의 첫 제목을 사용한다.
     * @param jsonProblem 문제
     * @param problemId 문제 id값
     */
    private void processProblemTitle(JsonObject jsonProblem, long problemId) {
        JsonArray titlesArray = jsonProblem.getAsJsonArray("titles");
        if (!titlesArray.isEmpty()) {
            String problemTitle = StreamSupport.stream(titlesArray.spliterator(), false)
                    .map(JsonObject.class::cast)
                    .filter(title -> title.get("language").getAsString().equals("ko"))
                    .findFirst()
                    .orElse(jsonProblem.getAsJsonArray("titles").get(0).getAsJsonObject())
                    .get("title").getAsString();

            int level = jsonProblem.get("level").getAsInt();
            long acceptedUserCount = jsonProblem.get("acceptedUserCount").getAsLong();
            int averageTries = jsonProblem.get("averageTries").getAsInt();

            problemsToSave.add(new Object[]{
                    problemId, problemTitle, level, acceptedUserCount, averageTries
            });
        }
    }
}