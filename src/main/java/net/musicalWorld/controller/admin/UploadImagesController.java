package net.musicalWorld.controller.admin;

import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class UploadImagesController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FileUtil fileUtil;

    @PostMapping("/change/main/image")
    public @ResponseBody
    String uploadMainImage(@RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty() && fileUtil.isValidImgFormat(file.getContentType())) {
            fileUtil.saveImage("", "home.jpg", file.getBytes());
            LOGGER.debug("main image updated");

        }
        return "home.jpg";
    }

    @PostMapping("/change/musician/image")
    public @ResponseBody
    String uploadMusicianImage(@RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty() && fileUtil.isValidImgFormat(file.getContentType())) {
            fileUtil.saveImage("", "musician.jpg", file.getBytes());
            LOGGER.debug("musician image updated");
        }
        return "musician.jpg";
    }
}
