package darkoverload.itzip.image.controller;


import darkoverload.itzip.image.controller.request.ImageDeleteRequest;
import darkoverload.itzip.image.controller.request.ImageUploadRequest;
import darkoverload.itzip.image.controller.response.ImageResponse;
import darkoverload.itzip.image.domain.Image;
import darkoverload.itzip.image.service.CloudStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static darkoverload.itzip.image.domain.ImageConst.TEMPDIR;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final CloudStorageService storageService;

    /**
     * 임시 파일 저장 Controller
     * @param multipartFiles
     * @return
     */
    @PostMapping("/temp")
    public ResponseEntity<Object> tempImageUpload(@RequestParam("files") List<MultipartFile> multipartFiles) {

            List<Image> images = new ArrayList<>();
            multipartFiles.forEach(file-> images.add(storageService.temporaryImageUpload(file, TEMPDIR)));

            List<ImageResponse> result = new ArrayList<>();
            makeImageResponse(images, result);

            Map<String, List<ImageResponse>> response = new HashMap<>();
            response.put("result", result);
            return ResponseEntity.ok(response);
    }

    /**
     * 실제 파일 저장 Controller
     * @param request
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Object> imageUpload(@RequestBody ImageUploadRequest request) {
        List<Image> images = new ArrayList<>();
        request.getImagePaths().forEach(path-> images.add(storageService.imageUpload(path, request.getFeatureDir())));

        List<ImageResponse> result = new ArrayList<>();
        makeImageResponse(images, result);
        Map<String, List<ImageResponse>> response = new HashMap<>();

        response.put("result", result);

        return ResponseEntity.ok(response);
    }

    /**
     * 파일 삭제 Controller
     * @param request
     * @return
     */
    @DeleteMapping("")
    public String fileDelete(@RequestBody ImageDeleteRequest request) {
        request.getImagePaths().forEach(path-> storageService.imageDelete(path, request.getFeatureDir()));
        return "이미지 삭제 성공";
    }

    /**
     * 파일 전체 공통 응답 값을 내려주기 위한 메서드
     * @param images
     * @param result
     */
    private static void makeImageResponse(List<Image> images, List<ImageResponse> result) {
        for(Image image : images) {
            result.add(ImageResponse.builder()
                    .imageSeq(image.getImageSeq())
                    .imagePath(image.getImagePath())
                    .build());
        }
    }

}
