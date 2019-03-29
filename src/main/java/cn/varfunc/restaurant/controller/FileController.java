package cn.varfunc.restaurant.controller;

import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    private final MinioClient client;

    @Autowired
    public FileController(MinioClient client) {
        this.client = client;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String upload(@RequestParam(name = "img") MultipartFile img) {
        UUID uuid = UUID.randomUUID();
        //noinspection TryWithIdenticalCatches
        try {
            if (img.isEmpty()) {
                log.warn("empty file!");
                return null;
            } else {
                client.putObject("restaurant", uuid.toString(), img.getInputStream(),
                        "application/octet-stream");
                log.info("upload successful");
            }
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return uuid.toString();
    }
}
