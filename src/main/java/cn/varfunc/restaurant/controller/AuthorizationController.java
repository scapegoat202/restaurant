package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.LoginForm;
import cn.varfunc.restaurant.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private final StoreService storeService;

    @Autowired
    public AuthorizationController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/login")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Boolean validateLogin(@RequestBody LoginForm form) {
        return storeService.validateUsernameAndPassword(form);
    }
}
