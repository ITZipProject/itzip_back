package darkoverload.itzip.infra.bucket.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class S3LocalStackBucketTest {

    @Autowired
    private AmazonS3 amazonS3;

    @Test
    public void testBucketCreation() {
        String bucketName = "test-bucket";
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
        assertTrue(amazonS3.doesBucketExistV2(bucketName));
    }
}
