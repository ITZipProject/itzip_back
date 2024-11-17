package darkoverload.itzip.feature.resume.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * StringListConverter 클래스는 JPA AttributeConverter를 구현하여
 * List<String> 타입을 JSON 문자열로 변환하여 DB에 저장하고,
 * DB에 저장된 JSON 문자열을 다시 List<String> 객체로 변환합니다.
 */
@Slf4j
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ",";
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    /**
     * List<String> 객체를 JSON 문자열로 변환하여 DB에 저장하는 메서드입니다.
     *
     * @param attribute 변환할 엔티티의 속성 값, List<String> 타입의 데이터
     * @return JSON 형식의 문자열로 변환된 데이터 (DB에 저장될 값)
     * @throws RestApiException JSON 처리 중 예외가 발생할 경우, BAD_REQUEST 예외를 발생시킵니다.
     */
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.debug("StringListConverter.convertToDatabaseColumn exception occur attribute: {}", attribute.toString());
            throw new RestApiException(CommonExceptionCode.BAD_REQUEST);
        }
    }

    /**
     * DB에 저장된 JSON 문자열 데이터를 List<String> 객체로 변환하는 메서드입니다.
     *
     * @param dbData DB로부터 가져온 JSON 문자열 데이터
     * @return JSON 문자열을 파싱한 후 List<String> 타입으로 변환한 객체
     * @throws RestApiException JSON 파싱 중 예외가 발생할 경우, BAD_REQUEST 예외를 발생시킵니다.
     */
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.asList(dbData.split(SPLIT_CHAR));
    }
}
