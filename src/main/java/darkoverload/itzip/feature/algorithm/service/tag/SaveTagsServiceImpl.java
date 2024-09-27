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
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SaveTagsServiceImpl implements SaveTagsService {
    private final SolvedAcAPI solvedAcAPI;
    private final ProblemTagRepository problemTagRepository;

    /**
     * Solved.ac API를 호출하여 문제 태그 정보를 가져와 데이터베이스에 저장하는 메서드.
     * 1페이지부터 5페이지까지의 태그 데이터를 가져와 파싱 후 저장한다.
     * 각 태그의 한국어 이름을 우선적으로 사용하며, 한국어 이름이 없을 경우 첫 번째 이름을 사용한다.
     * 예외 발생 시, RestApiException으로 변환하여 처리
     */
    @Override
    public void saveProblemTags() {
        List<Object[]> tagsToSave = IntStream.range(1, 6)  // page 1부터 5까지 반복
                .mapToObj(page -> {
                    try {
                        HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getTag(page));
                        JsonObject tags = JsonParser.parseString(response.body()).getAsJsonObject();
                        JsonArray items = tags.get("items").getAsJsonArray();

                        // items 배열을 스트림으로 변환하여 처리
                        return StreamSupport.stream(items.spliterator(), false)
                                .map(itemElement -> {
                                    JsonObject item = itemElement.getAsJsonObject();

                                    // displayNames에서 "ko" 찾아서 한국어 tag이름 붙이기 없으면 가장 첫번째꺼
                                    JsonArray displayNames = item.getAsJsonArray("displayNames");
                                    JsonObject displayName = StreamSupport.stream(displayNames.spliterator(), false)
                                            .map(JsonObject.class::cast)
                                            .filter(dn -> dn.get("language").getAsString().equals("ko"))
                                            .findFirst()
                                            .orElse(displayNames.get(0).getAsJsonObject());

                                    String name = displayName.has("name") ? displayName.get("name").getAsString() : "";
                                    String nameSort = displayName.has("short") ? displayName.get("short").getAsString() : "";

                                    // ProblemTag 객체 생성
                                    return new Object[]{item.get("bojTagId").getAsLong(), name, nameSort, item.get("problemCount").getAsInt()};
                                }).toList();
                    } catch (IOException e) {
                        throw new RestApiException(CommonExceptionCode.SOLVED_TAG_ERROR);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RestApiException(CommonExceptionCode.SOLVED_TAG_ERROR);
                    }
                })
                .flatMap(List::stream)  // 각 페이지의 결과를 하나의 리스트로 합침
                .toList();

        // DB에 저장
        problemTagRepository.batchInsertTags(tagsToSave);
    }
}