package cn.com.bocd.opencbsboot.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // @formatter:off
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return  new InMemoryUserDetailsManager(user);
    }
    // @formatter:on
}