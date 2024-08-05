package darkoverload.itzip.school.domain;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientWrapper implements WebClient {
    private final WebClient webClient;
    public WebClientWrapper(WebClient.Builder builder) {
        webClient = builder.build();
    }

    @Override
    public RequestHeadersUriSpec<?> get() {
        return webClient.get();
    }

    @Override
    public RequestHeadersUriSpec<?> head() {
        return webClient.head();
    }

    @Override
    public RequestBodyUriSpec post() {
        return webClient.post();
    }

    @Override
    public RequestBodyUriSpec put() {
        return webClient.put();
    }

    @Override
    public RequestBodyUriSpec patch() {
        return webClient.patch();
    }

    @Override
    public RequestHeadersUriSpec<?> delete() {
        return webClient.delete();
    }

    @Override
    public RequestHeadersUriSpec<?> options() {
        return webClient.options();
    }

    @Override
    public RequestBodyUriSpec method(HttpMethod method) {
        return webClient.method(method);
    }

    @Override
    public Builder mutate() {
        return webClient.mutate();
    }
}
