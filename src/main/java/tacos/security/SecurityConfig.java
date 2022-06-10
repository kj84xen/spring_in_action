package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
            .antMatchers("/design", "/orders/**")
            .permitAll()
            //.access("hasRole('ROLE_USER')")
            .antMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
            .antMatchers("/**").access("permitAll")

            .and()
            .formLogin()
            .loginPage("/login")

            .and()
            .httpBasic()
            .realmName("Taco Cloud")

            .and()
            .logout()
            .logoutSuccessUrl("/")

            .and()
            .csrf()
            .ignoringAntMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**")

            // Allow pages to be loaded in frames from the same origin; needed for H2-Console
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
