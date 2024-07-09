package darkoverload.itzip.sample.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("redisSample")
public class SampleRedis {
    @Id
    private String id;

    private String name;

    public SampleRedis(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
