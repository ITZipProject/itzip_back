package darkoverload.itzip.global.config.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
    void whenRestApiException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/test/restApiException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("잘못된 매개 변수가 포함됨"));
    }

    @Test
    void whenRestApiException2_thenReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/test/restApiException2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.message").value("인증이 필요합니다"));
    }

    @Test
    void whenRestApiException3_thenReturnsForbidden() throws Exception {
        mockMvc.perform(get("/test/restApiException3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("FORBIDDEN"))
                .andExpect(jsonPath("$.message").value("접근 권한이 없습니다"));
    }

    @Test
    void whenRestApiException4_thenReturnsNotFound() throws Exception {
        mockMvc.perform(get("/test/restApiException4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("리소스를 찾을 수 없습니다"));
    }

    @Test
    void whenException_thenReturnsInternalServerError() throws Exception {
        mockMvc.perform(get("/test/exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("서버 내부 오류입니다"));
    }

    @Test
    void whenException2_thenReturnsInternalServerError() throws Exception {
        mockMvc.perform(get("/test/exception2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("서버 내부 오류입니다"));
    }

    @Test
    void whenIllegalArgumentException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/test/illegalArgumentException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("잘못된 인자입니다"));
    }

    @Test
    void whenIllegalArgumentException2_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/test/illegalArgumentException2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUnsupportedOperationException_thenReturnsUnsupportedMediaType() throws Exception {
        mockMvc.perform(get("/test/unsupportedOperationException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.code").value("UNSUPPORTED_MEDIA_TYPE"))
                .andExpect(jsonPath("$.message").value("지원되지 않는 작업입니다"));
    }

    @Test
    void whenUnsupportedOperationException2_thenReturnsUnsupportedMediaType() throws Exception {
        mockMvc.perform(get("/test/unsupportedOperationException2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType());
    }
}