package darkoverload.itzip.resume.util;



import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.resume.code.SchoolType;
import darkoverload.itzip.resume.domain.School;
import darkoverload.itzip.resume.entity.SchoolEntity;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

import static darkoverload.itzip.resume.code.EstType.fromEstTypeName;
import static darkoverload.itzip.resume.code.RegionType.fromRegionName;

@Slf4j
public class JsonUtil {

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

    public static List<SchoolEntity> getSchoolInfo(String json, SchoolType schoolType){
        JSONParser parser = new JSONParser();
        List<SchoolEntity> list = new ArrayList<>();
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
                        .schoolName(schoolName).region( fromRegionName(region))
                        .estType(fromEstTypeName(estType))
                        .campusName(campusName)
                        .build();

                log.info("school insert data : {} ", school);

                list.add(school.convertToEntity());

            }

        } catch (ParseException e) {
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }

        return list;
    }
}
