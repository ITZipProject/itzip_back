package darkoverload.itzip.feature.techinfo.mock;

import darkoverload.itzip.feature.techinfo.domain.scrap.Scrap;

public class ScrapMockData {

    public static Scrap scrapDataOne = Scrap.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(100L)
            .build();

    public static Scrap scrapDataSecond = Scrap.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(101L)
            .build();

    public static Scrap scrapDataThree = Scrap.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(102L)
            .build();

}
