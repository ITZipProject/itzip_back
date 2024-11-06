package darkoverload.itzip.feature.algorithm.service.tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import darkoverload.itzip.feature.algorithm.repository.tag.ProblemTagRepository;
import darkoverload.itzip.feature.algorithm.util.SolvedAcAPI;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SaveTagsServiceImpl implements SaveTagsService {
    private final SolvedAcAPI solvedAcAPI;
    private final ProblemTagRepository problemTagRepository;

    private final List<Object[]> tagsToSave = new ArrayList<>();

    /**
     * Solved.ac API를 호출하여 문제 태그 정보를 가져와 데이터베이스에 저장하는 메서드.
     * 1페이지부터 5페이지까지의 태그 데이터를 가져와 파싱 후 저장한다.
     * 각 태그의 한국어 이름을 우선적으로 사용하며, 한국어 이름이 없을 경우 첫 번째 이름을 사용한다.
     * 예외 발생 시, RestApiException으로 변환하여 처리
     */
    @Override
    public void saveProblemTags() {
        IntStream.range(1, 6).forEach(this::processPage);

        // DB에 저장
        problemTagRepository.batchInsertTags(tagsToSave);
    }

    /**
     * solved api에 있는 모둔 tag 페이지를 호출해주는 메서드
     * @param page 각 페이지 번호
     */
    private void processPage(int page) {
        try {
            HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getTag(page));
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray jsonTags = jsonObject.get("items").getAsJsonArray();
            processTags(jsonTags);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestApiException(CommonExceptionCode.SOLVED_TAG_ERROR);
        }
    }

    /**
     * tags들에서 tag를 뽑아내는 메서드
     * @param tags tag들을 받아온다.
     */
    private void processTags(JsonArray tags) {
        StreamSupport.stream(tags.spliterator(), false)
                .forEach(tag -> processTag(tag.getAsJsonObject()));
    }

    /**
     * tag안에 있는 값들을 저장하는 메서드
     * @param tag tag객체 한개
     */
    private void processTag(JsonObject tag) {
        JsonArray displayNames = tag.getAsJsonArray("displayNames");
        JsonObject displayName = StreamSupport.stream(displayNames.spliterator(), false)
                .map(JsonObject.class::cast)
                .filter(dn -> dn.get("language").getAsString().equals("ko"))
                .findFirst()
                .orElse(displayNames.get(0).getAsJsonObject());

        String name = displayName.has("name") ? displayName.get("name").getAsString() : "";
        String nameSort = displayName.has("short") ? displayName.get("short").getAsString() : "";

        tagsToSave.add(new Object[]{
                tag.get("bojTagId").getAsLong(), name, nameSort, tag.get("problemCount").getAsInt()
        });
    }
}