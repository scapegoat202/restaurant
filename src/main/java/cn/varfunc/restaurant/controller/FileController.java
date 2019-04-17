package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFile(@RequestParam(name = "file") MultipartFile file,
                             @Value("${cn.varfunc.restaurant.minio.bucket-name}") String bucketName)
            throws IOException {
        UUID uuid = UUID.randomUUID();
        fileService.putFile(bucketName, uuid, file.getInputStream(), file.getSize(), file.getContentType());

        log.debug("image uuid: {}", uuid.toString());

        return uuid.toString();
    }
}
