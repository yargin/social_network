package com.getjavajob.training.yarginy.socialnetwork.web.configuration;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.LoginFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGIN;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGOUT;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().anonymous().disable();
        http.formLogin().loginPage(LOGIN).usernameParameter("email").passwordParameter("password").
                defaultSuccessUrl(ACCOUNT_WALL).failureHandler(loginFailureFilter());
        http.logout().logoutUrl(LOGOUT).logoutSuccessUrl(LOGIN).deleteCookies("JSESSIONID");
        http.rememberMe().key("uniqueAndSecret").userDetailsService(userDetailsService());
        http.exceptionHandling().accessDeniedPage(LOGIN);
        http.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public LoginFailureHandler loginFailureFilter() {
        return new LoginFailureHandler();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        AuthService authService = new AuthService();
        PasswordDaoFacade passwordDaoFacade = getApplicationContext().getBean(PasswordDaoFacade.class);
        authService.setPasswordDaoFacade(passwordDaoFacade);
        return authService;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        PasswordEncoder passwordEncoder = getApplicationContext().getBean(PasswordEncoder.class);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }
}
