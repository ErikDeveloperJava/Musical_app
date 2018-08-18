package net.musicalWorld.config.security;

import net.musicalWorld.model.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("UDS")
    private UserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/home","/news","/musicians")
                .permitAll()
                .antMatchers("/login","/register")
                .anonymous()
                .antMatchers("/admin","/admin/**")
                .hasAuthority(UserRole.ADMIN.name())
                .antMatchers("/categories")
                .hasAnyAuthority(UserRole.USER.name(),UserRole.ROLE_ANONYMOUS.name())
                .antMatchers("/user/bookmarks","/user/bookmarks/count")
                .hasAuthority(UserRole.USER.name())
        .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
        .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("RM")
                .tokenValiditySeconds(2000000)
                .userDetailsService(userDetailsService)
        .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("RM")
                .invalidateHttpSession(true)
        .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }
}
