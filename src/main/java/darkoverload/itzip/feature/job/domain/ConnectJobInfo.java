package darkoverload.itzip.feature.job.domain;

import darkoverload.itzip.feature.job.util.JobInfoJsonUtil;
import darkoverload.itzip.global.config.webclient.WebClientWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Slf4j
public class ConnectJobInfo {
    private static final WebClientWrapper webClientWrapper = new WebClientWrapper(WebClient.builder());

    /**
     * 주어진 API URL과 API 키를 사용하여, API로부터 총 데이터 수를 가져옵니다.
     *
     * @param apiUrl API 엔드포인트 URL
     * @param apiKey API 접근 키
     * @return API로부터 반환된 총 데이터 수
     */
    public static int getTotalCount(String apiUrl, String apiKey){
        // API 호출을 위한 URL을 생성, 파라미터로 액세스 키와 필요한 필터 조건을 추가
        String makeUrl = apiUrl+"?access-key="+apiKey+"&ind_cd=3&job_mid_cd=2&start=0&count=10";

        log.info("makeUrl :: {}", makeUrl);
        // WebClient를 사용하여 API에 요청을 보내고, 응답을 JSON 문자열로 받아옴
        String json = webClientWrapper.get().uri(makeUrl).retrieve().bodyToMono(String.class).block();

        // 받아온 JSON 데이터를 파싱하여 총 데이터 수를 추출
        int totalCount = JobInfoJsonUtil.getTotalCount(json);
        log.info("totalCount :: {}", totalCount);

        // 총 데이터 수를 반환
        return totalCount;
    }

    /**
     * 주어진 API URL과 API 키, 시작 인덱스를 사용하여 API로부터 JobInfo 데이터를 가져옵니다.
     * 가져온 데이터를 파싱하여 JobInfo 객체 리스트로 반환합니다.
     *
     * @param apiUrl API 엔드포인트 URL
     * @param apiKey API 접근 키
     * @param start 데이터 가져오기 시작 위치 (페이징을 위한 시작 인덱스)
     * @return API로부터 가져온 JobInfo 객체의 리스트
     */
    public static List<JobInfo> getJobInfoData(String apiUrl, String apiKey, int start) {
        // WebClientWrapper 인스턴스를 생성하여 WebClient를 초기화
        final WebClientWrapper webClientWrapper = new WebClientWrapper(WebClient.builder());

        // API 호출을 위한 URL을 생성, 파라미터로 액세스 키, 산업 코드, 중간 직무 코드, 시작 인덱스 및 최대 500개의 데이터를 가져오도록 설정
        String makeUrl = apiUrl+"?access-key="+apiKey+"&ind_cd=3&job_mid_cd=2&start="+start+"&count=500";

        // WebClient를 사용하여 API에 요청을 보내고, 응답을 JSON 문자열로 받아옴
        String json = webClientWrapper.get().uri(makeUrl).retrieve().bodyToMono(String.class).block();

        // 받아온 JSON 데이터를 파싱하여 JobInfo 객체의 리스트로 변환하여 반환
        return JobInfoJsonUtil.getInfoData(json);
    }


}
