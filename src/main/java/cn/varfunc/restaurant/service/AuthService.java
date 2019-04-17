package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String login(String username, String password) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails.getPassword().equals(password)) {
            return JwtUtils.generateToken(userDetails);
        } else {
            throw new BadCredentialsException("Wrong password");
        }
    }
}
