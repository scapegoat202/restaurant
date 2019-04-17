package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.LoginForm;
import cn.varfunc.restaurant.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private final AuthService authService;

    @Autowired
    public AuthorizationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String validateLogin(@RequestBody LoginForm form) {
        return authService.login(form.getUsername(), form.getPassword());
    }
}
