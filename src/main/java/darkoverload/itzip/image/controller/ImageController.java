package darkoverload.itzip.image.controller;


import darkoverload.itzip.image.service.CloudStorageService;
import darkoverload.itzip.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final CloudStorageService storageService;

    @PostMapping("")
    public String imageUpload(@RequestParam("files") List<MultipartFile> multipartFiles, @RequestParam("featureDir") String featureDir) {
            multipartFiles.forEach(file-> storageService.temporaryImageUpload(file, featureDir));

            return "success";
    }
}
