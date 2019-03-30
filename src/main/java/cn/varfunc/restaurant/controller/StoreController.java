package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.StoreForm;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.service.FileService;
import cn.varfunc.restaurant.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;
    private final FileService fileService;
    private final String bucketName;

    @Autowired
    public StoreController(StoreService storeService, FileService fileService,
                           @Value("${cn.varfunc.restaurant.minio.bucket-name}") String bucketName) {
        this.storeService = storeService;
        this.fileService = fileService;
        this.bucketName = bucketName;
    }

    /**
     * Get a store's information by given id, encode the <code>id</code> in the <code>url</code>
     */
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Store getStore(@PathVariable long id) {
        Store store = storeService.findById(id);
        String url = fileService.getFileURL(this.bucketName, store.getImageUUID());
        return store.setImageURL(url);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Store getStoreByUsername(@RequestParam String username) {
        Store store = storeService.findByUsername(username);
        String url = fileService.getFileURL(this.bucketName, store.getImageUUID());
        return store.setImageURL(url);
    }

    /**
     * Create a new Store<br>
     * Required field: <code>name</code>
     */
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Store createStore(@RequestBody StoreForm form) {
        final String username = Objects.requireNonNull(form.getUsername());
        final String password = Objects.requireNonNull(form.getPassword());
        final String name = Objects.requireNonNull(form.getName());
        Store store = storeService.create(username, password, name, form.getPhoneNumber(), form.getAnnouncement(),
                form.getAddress(), form.getWorkingGroup(), form.getUuid());
        String url = fileService.getFileURL(this.bucketName, store.getImageUUID());
        return store.setImageURL(url);
    }

    /**
     * Modify specified store's information by given id, only modified fields are needed
     */
    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Store modifyStoreInformation(@PathVariable long id, @RequestBody @Validated StoreForm form) {
        Store store = storeService.modifyInformation(id, form.getName(), form.getAnnouncement(),
                form.getWorkingGroup(), form.getPhoneNumber(), form.getAddress());
        String url = fileService.getFileURL(this.bucketName, store.getImageUUID());
        return store.setImageURL(url);
    }
}
