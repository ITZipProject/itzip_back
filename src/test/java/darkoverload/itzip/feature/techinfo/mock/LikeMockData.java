package darkoverload.itzip.feature.techinfo.mock;

import darkoverload.itzip.feature.techinfo.domain.like.Like;

public class LikeMockData {

    public static Like likeDataOne = Like.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(100L)
            .build();

    public static Like likeDataSecond = Like.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(101L)
            .build();

    public static Like likeDataThree = Like.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(102L)
            .build();

}
