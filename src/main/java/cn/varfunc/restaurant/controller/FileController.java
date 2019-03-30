package cn.varfunc.restaurant.controller;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {
    private final MinioClient client;

    @Autowired
    public FileController(MinioClient client) {
        this.client = client;
    }

    @PostMapping("/upload")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFile(@RequestParam(name = "file") MultipartFile file,
                             @Value("${cn.varfunc.restaurant.minio.bucket-name}") String bucketName) {

        UUID uuid = UUID.randomUUID();
        //noinspection TryWithIdenticalCatches
        try {
            this.client.putObject(bucketName, uuid.toString(), file.getInputStream(), file.getSize(), file.getContentType());
            System.out.println(file.getContentType());
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
