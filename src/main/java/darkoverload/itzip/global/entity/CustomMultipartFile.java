package darkoverload.itzip.global.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 멀티파일 생성을 위함 클래스
 */
public class CustomMultipartFile implements MultipartFile {

    private final byte[] fileContent;
    private final String fileName;
    private final String contentType;

    public CustomMultipartFile(byte[] fileContent, String fileName, String contentType) {
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File dest) throws IOException {
        try (FileOutputStream out = new FileOutputStream(dest)) {
            out.write(fileContent);
        }
    }
}