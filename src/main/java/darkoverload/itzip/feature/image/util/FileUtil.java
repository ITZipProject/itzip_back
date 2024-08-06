package darkoverload.itzip.feature.image.util;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.apache.tika.Tika;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;


@Slf4j
public class FileUtil {
    /**
     * 랜덤 파일이름 생성
     * @param filename 파일이름
     * @return 파일 이름 생성
     */
    public static String generateFileName(String filename) {

        return UUID.randomUUID() + fileExtension(filename);
    }

    /**
     * 파일 확장자 이름 가져오기
     * @param filename 파일이름
     * @return 파일 확장자
     */
    public static String fileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."), filename.length());
    }

    /**
     * 이미지 MimeType 확장자 체크
     * @param stream 스트림
     * @return 확장 체크 True, False 반환
     * @throws IOException
     */
    public static boolean imageExtensionCheck(InputStream stream) throws IOException {
        Tika tika = new Tika();

        String mimeType = tika.detect(stream);
        log.info("MIME TYPE = {}", mimeType);

        return mimeType.startsWith("image/png") || mimeType.startsWith("image/jpeg") || mimeType.startsWith("image/jpg") || mimeType.startsWith("image/gif");
    }

    /**
     * 확장자 파일을 가져오기 위한 유틸
     * @param stream 파일 스트림
     * @return 확장자명
     * @throws IOException
     */
    public static String getMimeType(InputStream stream) throws IOException {
        Tika tika = new Tika();

        return tika.detect(stream);
    }

    /**
     * multipartfile 실제 파일로 변경
     * @param mfile 멀티 파트 파일
     * @return 실제 파일
     * @throws IOException
     */
    public File convert(MultipartFile mfile) throws IOException {

        File file  = new File(mfile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mfile.getBytes());
        fos.close();

        return file;
    }

    /**
     * 이미지 사이즈 조정 (썸네일) 추후 정해지면 적용 예정
     * @param fileFormatName
     * @param originalImage
     * @param targetWidth
     * @return 사이즈 조정 파일 InputStream
     */
    public static InputStream resizeImage(String fileFormatName, MultipartFile originalImage, int targetWidth) {

        try{
            // MultipartFile -> BufferedImageConvert
            BufferedImage image = ImageIO.read(originalImage.getInputStream());
            log.info("image :: {} ", image.getHeight());
            log.info("image :: {} ", image.getWidth());
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            // 원본 사이즈가 target size width 보다 작으면 원본 반환 해준다.
            if(originWidth < targetWidth)
                return originalImage.getInputStream();

            MarvinImage imageMarvin = new MarvinImage(image);

            // 이미지 리사이징
            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", targetWidth);
            scale.setAttributes("newHeight", targetWidth * originHeight / originWidth);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            // aplpha부분 없애주어 이미지 최적화
            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormatName, baos);
            baos.flush();

            return new ByteArrayInputStream(baos.toByteArray());
        } catch(IOException e) {
            throw new RestApiException(CommonExceptionCode.IMAGE_RESIZE_ERROR);
        }

    }

}
