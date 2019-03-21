package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.LoginForm;
import cn.varfunc.restaurant.domain.response.ApiResponse;
import cn.varfunc.restaurant.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private final StoreService storeService;

    @Autowired
    public AuthorizationController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/login")
    public ApiResponse validateLogin(@RequestBody LoginForm form) {
        return ApiResponse.builder()
                .data(storeService.validateUsernameAndPassword(form))
                .build();
    }
}
