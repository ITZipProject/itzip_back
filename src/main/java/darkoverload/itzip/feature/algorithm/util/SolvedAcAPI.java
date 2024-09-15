package darkoverload.itzip.feature.algorithm.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SolvedAcAPI {
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${SOLVED_AC_URL}")
    private String solvedAcUrL;

    //uri요청이 있을시 요청을 받아주고 데이터 받아옴
    public HttpResponse<String> solvedacAPIRequest(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("x-solvedac-language", "ko")
                .header("Accept", "application/json")
                .GET()
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    //사용자 이름으로 사용자 정보 가져오기
    public String getUserByName(String User) {
        return solvedAcUrL + "search/user?query=" + User;
    }


    //사용자 이름으로 사용자가 푼 문제 가져오기
    public String getUserSolvedProblemByName(String User, int page) {
        return solvedAcUrL + "search/problem?query=s%40" + User + "&direction=asc&page=" + page +"&sort=id";
    }


    //백준 문제 숫자열로 가져오기
    public String getProblemByArray(List<Integer> problemIds) {
        String problemIdsParam = problemIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return solvedAcUrL + "problem/lookup?problemIds=" + problemIdsParam;
    }

    //문제 tag들 가져오기
    public String getTag(int page){
        return solvedAcUrL + "search/tag?query&page=" + page;
    }
}
