package darkoverload.itzip.feature.school.util;



import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.domain.School;
import darkoverload.itzip.feature.school.entity.SchoolDocument;
import darkoverload.itzip.feature.school.entity.SchoolEntity;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

import static darkoverload.itzip.feature.school.code.EstType.fromEstTypeName;
import static darkoverload.itzip.feature.school.code.RegionType.fromRegionName;


@Slf4j
public class SchoolJsonUtil {

    /**
     * JSON 데이터에서 총 개수를 추출해준다.
     * @param json JSON 데이터 문자열
     * @return 총 개수
     */
    public static String getTotalCount(String json){
        JSONParser parser = new JSONParser();
        JSONObject targetObject = null;
        try {
            targetObject = (JSONObject) parser.parse(json);
            JSONObject dataSearch = (JSONObject) targetObject.get("dataSearch");
            JSONArray obj = (JSONArray) dataSearch.get("content");
            JSONObject data = (JSONObject) obj.getFirst();

            return (String) data.get("totalCount");
        } catch (ParseException e) {
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 각 구분에 대한 학교 정보 데이터를 가져와준다.
     * @param json 학교 정보 JSON 데이터
     * @param schoolType 학교 구분 타입
     * @return 학교정보 엔티티 리스트
     */
    public static List<SchoolDocument> getSchoolInfo(String json, SchoolType schoolType){
        JSONParser parser = new JSONParser();
        List<SchoolDocument> list = new ArrayList<>();
        try {
            JSONObject targetObject = (JSONObject) parser.parse(json);
            JSONObject dataSearch = (JSONObject) targetObject.get("dataSearch");
            JSONArray array = (JSONArray) dataSearch.get("content");


            for(int i=0;i<array.size();i++){
                JSONObject object = (JSONObject) array.get(i);

                String schoolGubun = object.containsKey("schoolGubun") ? (String) object.get("schoolGubun") : "";
                String adres = object.containsKey("adres") ? (String) object.get("adres") : "";
                String schoolName = object.containsKey("schoolName") ? (String) object.get("schoolName") : "";
                String region = object.containsKey("region") ? (String) object.get("region") : "";
                String estType = object.containsKey("estType") ? (String) object.get("estType") : "";
                String campusName = object.containsKey("campusName") ? (String) object.get("campusName") : "";

                switch (schoolName) {
                    case "공군항공과학고등학교" -> {
                        estType = "국립";
                        adres = "경상남도 진주시 금산면 송백로 46(속사리, 공군교육사령부 내)";
                    }
                    case "반디기독학교" -> {
                        estType = "사립";
                        adres = "부산광역시 해운대구 해운대로 191번길 9 반디기독학교";
                    }
                }

                School school = School.builder()
                        .gubun(schoolGubun)
                        .address(adres)
                        .schoolType(schoolType)
                        .schoolName(schoolName).region(fromRegionName(region))
                        .estType(fromEstTypeName(estType))
                        .campusName(campusName)
                        .build();

                log.info("school insert data : {} ", school);

                list.add(school.covertToDocument());

            }

        } catch (ParseException e) {
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }

        return list;
    }
}
