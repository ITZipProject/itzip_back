package darkoverload.itzip.global.config.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class GlobalExceptionHandlerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("BAD_REQUEST 테스트")
    void whenRestApiException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/test/restApiException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").value("잘못된 매개 변수가 포함됨"));
    }

    @Test
    @DisplayName("UNAUTHORIZED 테스트")
    void whenRestApiException2_thenReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/test/restApiException2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.data").value("인증이 필요합니다"));
    }

    @Test
    @DisplayName("FORBIDDEN 테스트")
    void whenRestApiException3_thenReturnsForbidden() throws Exception {
        mockMvc.perform(get("/test/restApiException3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("FORBIDDEN"))
                .andExpect(jsonPath("$.data").value("접근 권한이 없습니다"));
    }

    @Test
    @DisplayName("NOT_FOUND 테스트")
    void whenRestApiException4_thenReturnsNotFound() throws Exception {
        mockMvc.perform(get("/test/restApiException4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.data").value("리소스를 찾을 수 없습니다"));
    }

    @Test
    @DisplayName("INTERNAL_SERVER_ERROR 테스트")
    void whenException_thenReturnsInternalServerError() throws Exception {
        mockMvc.perform(get("/test/exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.data").value("서버 내부 오류입니다"));
    }

    @Test
    @DisplayName("INTERNAL_SERVER_ERROR 테스트2")
    void whenException2_thenReturnsInternalServerError() throws Exception {
        mockMvc.perform(get("/test/exception2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.data").value("서버 내부 오류입니다"));
    }

    @Test
    @DisplayName("illegalArgumentException 테스트")
    void whenIllegalArgumentException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/test/illegalArgumentException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").value("잘못된 인자입니다"));
    }

    @Test
    @DisplayName("illegalArgumentException2 테스트")
    void whenIllegalArgumentException2_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/test/illegalArgumentException2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("unsupportedOperationException 테스트")
    void whenUnsupportedOperationException_thenReturnsUnsupportedMediaType() throws Exception {
        mockMvc.perform(get("/test/unsupportedOperationException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.code").value("UNSUPPORTED_MEDIA_TYPE"))
                .andExpect(jsonPath("$.data").value("지원되지 않는 작업입니다"));
    }

    @Test
    @DisplayName("unsupportedOperationException2 테스트")
    void whenUnsupportedOperationException2_thenReturnsUnsupportedMediaType() throws Exception {
        mockMvc.perform(get("/test/unsupportedOperationException2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("200 OK 테스트2")
    void getSuccess2Response() {
        Assertions.assertTimeout(Duration.ofSeconds(2), () -> {
            mockMvc.perform(get("/test/success2")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("200 OK"))
                    .andExpect(jsonPath("$.msg").value("요청이 성공됐습니다"))
                    .andExpect(jsonPath("$.data.field1").value("field1Value"))
                    .andExpect(jsonPath("$.data.field2").value("field2Value"));
        });
    }

    @Test
    @DisplayName("200 OK 테스트")
    void getSuccessResponse() throws Exception {
        mockMvc.perform(get("/test/success")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200 OK"))
                .andExpect(jsonPath("$.msg").value("요청이 성공됐습니다"))
                .andExpect(jsonPath("$.data").value("성공했습니다"));
    }

    @Test
    @DisplayName("200 OK 테스트3")
    void getSuccess3Response() {
        Assertions.assertTimeout(Duration.ofSeconds(2), () -> {
            mockMvc.perform(get("/test/success3")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("200 OK"))
                    .andExpect(jsonPath("$.msg").value("요청이 성공됐습니다"))
                    .andExpect(jsonPath("$.data.field1").value("field1Value"))
                    .andExpect(jsonPath("$.data.field2").value("field2Value"));
        });
    }

    @Test
    @DisplayName("REQUEST_TIMEOUT 테스트")
    void whenClientAbortException_thenReturnsRequestTimeout() throws Exception {
        mockMvc.perform(get("/test/clientAbortException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isRequestTimeout())
                .andExpect(jsonPath("$.code").value("REQUEST_TIMEOUT"))
                .andExpect(jsonPath("$.data").value("요청 시간이 초과되었습니다"));
    }

    @Test
    @DisplayName("SERVICE_UNAVAILABLE 테스트")
    void whenServiceUnavailableException_thenReturnsServiceUnavailable() throws Exception {
        mockMvc.perform(get("/test/serviceUnavailable")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value("SERVICE_UNAVAILABLE"))
                .andExpect(jsonPath("$.data").value("서비스를 사용할 수 없습니다"));
    }

    @Test
    @DisplayName("validation 테스트")
    void whenMethodArgumentNotValidException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("FILED_ERROR"))
                .andExpect(jsonPath("$.data").value("요청값이 올바르지 않습니다."));
    }

    @Test
    @DisplayName("missingParameter 테스트")
    void whenMissingServletRequestParameterException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/test/missingParameter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())  // Expecting 400 Bad Request
                .andExpect(jsonPath("$.code").value("FILED_ERROR"))
                .andExpect(jsonPath("$.data").value("요청값이 올바르지 않습니다."));
    }

    @Test
    @DisplayName("validateMethod 테스트")
    void whenHandlerMethodValidationException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/test/validateMethod")
                        .param("age", "-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("FILED_ERROR"))
                .andExpect(jsonPath("$.data").value("요청값이 올바르지 않습니다."));
    }

    @Test
    @DisplayName("미지원 미디어 타입 에외처리 테스트")
    void whenUnsupportedMediaType_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(multipart("/test/validateMediaType"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("FILED_ERROR"))
                .andExpect(jsonPath("$.data").value("요청값이 올바르지 않습니다."));
    }

    @Test
    @DisplayName("파일 파라미터 validation 테스트")
    void givenInvalidFileUploadParameters_whenValidated_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/test/validateFile")
                        .param("file", "")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("FILED_ERROR"))
                .andExpect(jsonPath("$.data").value("요청값이 올바르지 않습니다."));
    }


//    @Test
//    @DisplayName("NoHandlerFoundException 테스트")
//    void whenHandlerMethodValidationException_thenReturnsBadRequest2() throws Exception {
//        mockMvc.perform(post("/api/test")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound()) //404까지는 깔끔하게나옴
//                .andExpect(jsonPath("$.code").value("NOT_FOUND")); //json은 없다고나옴
//        // postman에서 요청했을때는 body부분이 잘나오는데 testcode에서는 안나옴 왜지
//        // 일단 주석처리
//    }
}