package darkoverload.itzip.image.util;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.image.exception.CustomImageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
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

        return mimeType.startsWith("image");
    }

    public static String getMimeType(InputStream stream) throws IOException {
        Tika tika = new Tika();

        return tika.detect(stream);
    }

    public File convert(MultipartFile mfile) throws IOException {

        File file  = new File(mfile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mfile.getBytes());
        fos.close();

        return file;
    }

//    public static InputStream resizeImage(String fileFormatName, MultipartFile originalImage, int targetWidth) {
//
//        try{
//            // MultipartFile -> BufferedImageConvert
//            BufferedImage image = ImageIO.read(originalImage.getInputStream());
//            log.info("image :: {} ", image.getHeight());
//            log.info("image :: {} ", image.getWidth());
//            int originWidth = image.getWidth();
//            int originHeight = image.getHeight();
//
//            if(originWidth < targetWidth)
//                return originalImage.getInputStream();
//
//            MarvinImage imageMarvin = new MarvinImage(image);
//
//            Scale scale = new Scale();
//            scale.load();
//            scale.setAttribute("newWidth", targetWidth);
//            scale.setAttributes("newHeight", targetWidth * originHeight / originWidth);
//            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);
//
//            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(imageNoAlpha, fileFormatName, baos);
//            baos.flush();
//
//            return new ByteArrayInputStream(baos.toByteArray());
//        } catch(IOException e) {
//            throw new CustomImageException(CommonExceptionCode.IMAGE_RESIZE_ERROR);
//        }
//
//    }

}
