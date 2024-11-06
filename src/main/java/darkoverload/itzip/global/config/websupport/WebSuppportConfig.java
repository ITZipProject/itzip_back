package darkoverload.itzip.global.config.websupport;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.PagedResourcesAssemblerArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSuppportConfig implements WebMvcConfigurer {

    /**
     * PagedResourcesAssembler를 빈으로 등록하는 메서드 PagedResourcesAssembler는 페이징 처리된 데이터에 HATEOAS 링크를 추가하는데 사용 이 빈은
     * customPagedResourcesAssembler라는 이름으로 등록
     *
     * @return PagedResourcesAssembler<?>의 인스턴스
     */
    @Bean(name = "customPagedResourcesAssembler")
    public PagedResourcesAssembler<?> pagedResourcesAssembler() {
        return new PagedResourcesAssembler<>(null, null);
    }

    /**
     * Spring MVC의 Argument Resolver 목록에 PagedResourcesAssemblerArgumentResolver를 추가하는 메서드 이 Resolver는 컨트롤러 메서드에서
     * PagedResourcesAssembler를 사용 가능하게 함. 기본 페이징 및 정렬 설정을 처리하고, 페이징된 리소스에 링크를 추가하는 역할을 함.
     *
     * @param resolvers 기존의 Argument Resolver 목록
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PagedResourcesAssemblerArgumentResolver(null));
    }

    /**
     * "/" 경로(메인페이지) 접근 시 swagger-ui 페이지로 리다이렉트
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui/index.html");
    }
}