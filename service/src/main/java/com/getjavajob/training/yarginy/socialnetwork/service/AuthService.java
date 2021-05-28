package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordDaoFacade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullPassword;

public class AuthService implements UserDetailsService {
    private PasswordDaoFacade passwordDaoFacade;

    public void setPasswordDaoFacade(PasswordDaoFacade passwordDaoFacade) {
        this.passwordDaoFacade = passwordDaoFacade;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = new Account();
        account.setEmail(s);
        Password password = new Password();
        password.setAccount(account);
        password = passwordDaoFacade.select(password);
        if (password.equals(getNullPassword())) {
            throw new UsernameNotFoundException("user not found");
        }
        account = password.getAccount();
        return new User(account.getEmail(), password.getStringPassword(), getAuthorities(account));
    }

    private Collection<GrantedAuthority> getAuthorities(Account account) {
        GrantedAuthority authority = new SimpleGrantedAuthority(account.getRole().toString());
        return Collections.singletonList(authority);
    }
}
