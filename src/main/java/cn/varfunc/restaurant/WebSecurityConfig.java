package cn.varfunc.restaurant;

import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.filter.JwtAuthenticationTokenFilter;
import cn.varfunc.restaurant.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final StoreService storeService;


    @Autowired
    public WebSecurityConfig(StoreService storeService) {
        this.storeService = storeService;
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(userDetailsService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/file/**").permitAll()
                .antMatchers(HttpMethod.POST, "/stores").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
        http.csrf().disable();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsServiceBean();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() {
        return username -> {
            Store store = storeService.findByUsername(username);
            return User.builder()
                    .username(store.getUsername())
                    .password(store.getPassword())
                    .authorities("USER")
                    .build();
        };
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder) {
        try {
            builder.userDetailsService(userDetailsService());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
