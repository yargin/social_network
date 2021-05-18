package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.ModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import static java.util.Objects.isNull;


@Service("authService")
public class RegisterServiceImpl implements RegisterService {
    private final AccountDaoFacade accountDaoFacade;
    private final PasswordDaoFacade passwordDaoFacade;
    private final PhoneDaoFacade phoneDaoFacade;
    private final ModelsFactory modelsFactory;
    private final transient PasswordEncoder passwordEncoder;

    public RegisterServiceImpl(AccountDaoFacade accountDaoFacade, PasswordDaoFacade passwordDaoFacade,
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
        if (isNull(account.getRole())) {
            account.setRole(Role.USER);
        }
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
}
