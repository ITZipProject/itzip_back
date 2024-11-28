package darkoverload.itzip.feature.techinfo.repository.blog.custom;

public interface CustomBlogCommandRepository {

    long update(Long userId, String newIntro);

    long update(Long blogId, boolean status);

}
