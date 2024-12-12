package darkoverload.itzip.feature.techinfo.mock;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;

public class CommentMockData {

    public static Comment commentDataOne = Comment.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(100L)
            .content("test1")
            .isPublic(true)
            .build();

    public static Comment commentDataSecond = Comment.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(101L)
            .content("test2")
            .isPublic(true)
            .build();

    public static Comment commentDataThree = Comment.builder()
            .postId("675979e6605cda1eaf5d4c17")
            .userId(102L)
            .content("test3")
            .isPublic(true)
            .build();

    public static Comment commentDataFour = Comment.builder()
            .id("675979e6605cda1eaf5d4c19")
            .postId("675979e6605cda1eaf5d4c17")
            .userId(103L)
            .content("test4")
            .isPublic(true)
            .build();

}
