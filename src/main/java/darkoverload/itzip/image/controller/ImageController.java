package darkoverload.itzip.image.controller;


import darkoverload.itzip.image.controller.request.ImageDeleteRequest;
import darkoverload.itzip.image.controller.request.ImageUploadRequest;
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

    @PostMapping("/temp")
    public ResponseEntity<Object> tempimageUpload(@RequestParam("files") List<MultipartFile> multipartFiles) {

            List<String> paths = new ArrayList<>();
            multipartFiles.forEach(file-> paths.add(storageService.temporaryImageUpload(file, TEMPDIR)));

            Map<String, List<String>> response = new HashMap<>();
            response.put("paths", paths);
            return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<Object> imageUpload(@RequestBody ImageUploadRequest request) {
        List<String> paths = new ArrayList<>();
        request.getImagePaths().forEach(path-> paths.add(storageService.imageUpload(path, request.getFeatureDir())));

        Map<String, List<String>> response = new HashMap<>();
        response.put("paths", paths);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public String fileDelete(@RequestBody ImageDeleteRequest request) {
        request.getImagePaths().forEach(path-> storageService.imageDelete(path, request.getFeatureDir()));
        return "이미지 삭제 성공";
    }
}
