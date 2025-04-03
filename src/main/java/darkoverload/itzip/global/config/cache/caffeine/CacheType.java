package darkoverload.itzip.global.config.cache.caffeine;

import lombok.Getter;

@Getter
public enum CacheType {

    ARTICLE_PREVIEW("articlesPreview", 1800, 2000);

    private final String name;
    private final int expiredAfterWrite;
    private final int maximumSize;

    CacheType(String name, int expiredAfterWrite, int maximumSize) {
        this.name = name;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }

}
