package darkoverload.itzip.feature.algorithm.service.problem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.entity.ProblemTagEntity;
import darkoverload.itzip.feature.algorithm.entity.ProblemTagMappingEntity;
import darkoverload.itzip.feature.algorithm.repository.mapping.ProblemTagMappingRepository;
import darkoverload.itzip.feature.algorithm.repository.problem.ProblemRepository;
import darkoverload.itzip.feature.algorithm.util.SolvedAcAPI;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveProblemsImpl implements SaveProblems {
    private final SolvedAcAPI solvedAcAPI;

    private final ProblemRepository problemRepository;
    private final ProblemTagMappingRepository problemTagMappingRepository;

    /**Todo
     *
     */
    @Override
    public void saveProblem(){
        int start = 1;
        int batchSize = 50;

        //batch를 위한 배열
        List<ProblemEntity> problemsToSave = new ArrayList<>();
        List<ProblemTagMappingEntity> mappingsToSave = new ArrayList<>();
        try {
            //총 문제 수 알아내기
            HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getProblemByPage(1));
            int end = JsonParser.parseString(response.body()).getAsJsonObject().get("count").getAsInt();
            end = end / 50 + 1;

            for (int i = start; i <= end; i++) {
                HttpResponse<String> problemsResponse = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getProblemByPage(i));
                JsonArray jsonProblems = JsonParser.parseString(response.body()).getAsJsonArray();
                for (int j = 0; j < jsonProblems.size(); j++) {
                    if (!jsonProblems.get(j).isJsonNull()) {
                        JsonObject jsonProblem = jsonProblems.get(j).getAsJsonObject();

                        String problemTitle = "";

                        JsonArray titles = jsonProblem.getAsJsonArray("titles");
                        for (int k = 0; k < titles.size(); k++) {
                            JsonObject title = titles.get(k).getAsJsonObject();
                            if (title.get("language").getAsString().equals("ko")) {
                                problemTitle = title.get("title").getAsString();
                                break;
                            } else {
                                problemTitle = title.get("title").getAsString();
                            }
                        }

                        ProblemEntity.builder()
                                .problemId(jsonProblem.get("problemId").getAsLong())
                                .level(jsonProblem.get("level").getAsInt())
                                .acceptedUserCount(jsonProblem.get("acceptedUserCount").getAsLong())
                                .title(problemTitle)
                                .averageTries(jsonProblem.get("averageTries").getAsInt())
                                .build();

                        JsonArray tags = jsonProblem.getAsJsonArray("tags");
                        for (int n = 0; n < tags.size(); n++) {
                            JsonObject tag = tags.get(n).getAsJsonObject();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.SOLVED_TAG_ERROR);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestApiException(CommonExceptionCode.SOLVED_TAG_ERROR);
        }
    }
}
