package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.ModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullPassword;


@Service("authService")
public class AuthServiceImpl implements AuthService {
    private final AccountDaoFacade accountDaoFacade;
    private final PasswordDaoFacade passwordDaoFacade;
    private final PhoneDaoFacade phoneDaoFacade;
    private final ModelsFactory modelsFactory;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AccountDaoFacade accountDaoFacade, PasswordDaoFacade passwordDaoFacade,
                           PhoneDaoFacade phoneDaoFacade, ModelsFactory modelsFactory,
                           @Qualifier("encoder") PasswordEncoder passwordEncoder) {
        this.accountDaoFacade = accountDaoFacade;
        this.passwordDaoFacade = passwordDaoFacade;
        this.phoneDaoFacade = phoneDaoFacade;
        this.modelsFactory = modelsFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(Account account, Collection<Phone> phones, String password) {
        account.setRegistrationDate(Date.valueOf(LocalDate.now()));
        if (!accountDaoFacade.create(account)) {
            throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
        }
        Account savedAccount = accountDaoFacade.select(account);
        for (Phone phone : phones) {
            phone.setOwner(savedAccount);
        }
        if (!phoneDaoFacade.create(phones)) {
            throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
        }
        Password passwordObject = modelsFactory.getPassword(savedAccount, passwordEncoder.encode(password));
        if (!passwordDaoFacade.create(passwordObject)) {
            throw new IllegalStateException();
        }
        return true;
    }

    @Override
    public Account login(String email, String password) {
        Password storedPassword = new Password();
        Account account = new Account();
        account.setEmail(email);
        storedPassword.setAccount(accountDaoFacade.select(account));
        storedPassword.setStringPassword(password);
        storedPassword = passwordDaoFacade.select(storedPassword);
        if (storedPassword.equals(getNullPassword())) {
            throw new IncorrectDataException(IncorrectData.WRONG_EMAIL);
        }
        if (!passwordEncoder.matches(password, storedPassword.getStringPassword())) {
            throw new IncorrectDataException(IncorrectData.WRONG_PASSWORD);
        }
        return accountDaoFacade.select(account);
    }
}
