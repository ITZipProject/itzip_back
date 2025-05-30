package darkoverload.itzip.feature.job.util;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.List;
import static darkoverload.itzip.feature.job.util.TimeStampUtil.convertToLocalDateTime;

@Slf4j
public class JobInfoJsonUtil {

    /**
     * 주어진 JSON 문자열에서 총 카운트를 추출하여 정수로 반환합니다.
     *
     * @param json 총 카운트를 포함하는 JSON 문자열
     * @return 총 카운트를 나타내는 정수 값
     * @throws RestApiException JSON 파싱 중 오류가 발생하거나 총 카운트가 정수로 변환될 수 없는 경우 발생
     */
    public static int getTotalCount(String json){
        // JSONParser 인스턴스를 생성하여 JSON 문자열을 파싱할 준비를 함
        JSONParser parser = new JSONParser();


        JSONObject targetObject = null;
        String total = null;
        try {

            // 주어진 JSON 문자열을 파싱하여 JSONObject로 변환
            targetObject = (JSONObject) parser.parse(json);

            // "jobs" 키에 해당하는 JSONObject를 추출
            JSONObject jobObject = (JSONObject) targetObject.get("jobs");

            // "total" 키에 해당하는 값을 문자열로 추출
            total = (String) jobObject.get("total");
        } catch (ParseException e) {
            // JSON 파싱 중 오류가 발생하면 RestApiException을 던짐
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }

        if(total == null) throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);

        // "total" 문자열을 정수로 변환하여 반환
        return Integer.parseInt(total);
    }

    /**
     * JSON 문자열에서 JobInfo 객체들의 리스트를 추출하여 반환합니다.
     * @param json 파싱할 JSON 문자열
     * @return 추출된 JobInfo 객체들의 리스트
     */
    public static List<JobInfo> getInfoData(String json){
        // JSON 문자열을 파싱하기 위한 JSONParser 객체 생성
        JSONParser parser = new JSONParser();

        // JobInfo 객체들을 담을 리스트 생성
        List<JobInfo> jobInfoList = new ArrayList<>();

        try{
            // JSON 문자열을 파싱하여 JSONObject로 변환
            JSONObject targetObject = (JSONObject) parser.parse(json);
            // "jobs" 키에 해당하는 JSONObject를 가져옴
            JSONObject jobJsonObjects = (JSONObject) targetObject.get("jobs");
            // "job" 키에 해당하는 JSONArray를 가져옴
            JSONArray array = (JSONArray) jobJsonObjects.get("job");

            // JSONArray를 순회하며 각 JSONObject를 처리
            for(int i=0; i< array.size(); i++){
                JSONObject object = (JSONObject) array.get(i);

                JobInfo jobInfo = getJobInfo(object);

                jobInfoList.add(jobInfo);

            }

        } catch (ParseException e) {
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }

        // JobInfo 객체들의 리스트 반환
        return jobInfoList;
    }

    /**
     * 주어진 JSONObject에서 JobInfo 객체를 생성하여 반환합니다.
     * @param object JobInfo를 생성하는 데 사용할 JSON 객체
     * @return JobInfo 객체
     */
    private static JobInfo getJobInfo(JSONObject object) {
        // JSON 객체에서 "url" 값을 추출
        String url = (String) object.get("url");

        // "company" JSON 객체 내에서 회사 정보를 추출
        JSONObject companyObject = (JSONObject) object.get("company");
        JSONObject companyDetail = (JSONObject) companyObject.get("detail");
        String companyName = (String) companyDetail.get("name");

        // "position" JSON 객체 내에서 포지션 정보를 추출
        JSONObject position = (JSONObject) object.get("position");
        String title = (String) position.get("title");

        // "industry" JSON 객체 내에서 산업 코드를 추출
        JSONObject industry = (JSONObject) position.get("industry");
        String industryName = (String) industry.get("name");

        // "location" JSON 객체 내에서 위치 정보를 추출
        JSONObject location = (JSONObject) position.get("location");
        String locationCode = (String) location.get("code");
        String locationName = (String) location.get("name");

        // "job-code" JSON 객체 내에서 직무 코드를 추출
        JSONObject job = (JSONObject) position.get("job-code");
        String jobName = (String) job.get("name");

        // "experience-level" JSON 객체 내에서 경력 정보를 추출
        JSONObject experienceLevel = (JSONObject) position.get("experience-level");
        Long experienceLevelMin = (Long) experienceLevel.get("min");
        Long experienceLevelMax = (Long) experienceLevel.get("max");
        String experienceLevelName = (String) experienceLevel.get("name");

        // JSON 객체에서 "keyword" 값을 추출
        String keyword = (String) object.get("keyword");

        // JSON 객체에서 "id", "expiration-timestamp", "posting-timestamp" 값을 추출
        String id = (String) object.get("id");
        String expirationTimeStamp = (String) object.get("expiration-timestamp");
        String postingTimeStamp = (String) object.get("posting-timestamp");

        return JobInfo.builder()
                .positionId(Long.valueOf(id))
                .companyName(companyName)
                .url(url)
                .title(title)
                .industryName(industryName)
                .locationCode(locationCode)
                .locationName(locationName)
                .jobName(jobName)
                .experienceMax(experienceLevelMax)
                .experienceMin(experienceLevelMin)
                .experienceName(experienceLevelName)
                .keyword(keyword)
                .expirationDate(convertToLocalDateTime(expirationTimeStamp))
                .postingDate(convertToLocalDateTime(postingTimeStamp))
                .build();
    }
}
