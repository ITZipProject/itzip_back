package darkoverload.itzip.global.config.elastic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "org.springframework.data.elasticsearch.repository")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${elastic.info}")
    private String info;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(info)
                .build();
    }
}
