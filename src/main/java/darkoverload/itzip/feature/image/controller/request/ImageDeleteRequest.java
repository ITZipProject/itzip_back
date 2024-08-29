package darkoverload.itzip.feature.image.controller.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageDeleteRequest {
    List<String> imagePaths;
    String featureDir;
}
