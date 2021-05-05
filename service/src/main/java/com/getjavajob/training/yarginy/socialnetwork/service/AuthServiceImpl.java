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
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullPassword;


@Service("authService")
public class AuthServiceImpl implements AuthService {
    private final AccountDaoFacade accountDaoFacade;
    private final PasswordDaoFacade passwordDaoFacade;
    private final PhoneDaoFacade phoneDaoFacade;
    private final DataHandler dataHandler;
    private final ModelsFactory modelsFactory;

    public AuthServiceImpl(DataHandler dataHandler, AccountDaoFacade accountDaoFacade,
                           PasswordDaoFacade passwordDaoFacade, PhoneDaoFacade phoneDaoFacade,
                            ModelsFactory modelsFactory) {
        this.dataHandler = dataHandler;
        this.accountDaoFacade = accountDaoFacade;
        this.passwordDaoFacade = passwordDaoFacade;
        this.phoneDaoFacade = phoneDaoFacade;
        this.modelsFactory = modelsFactory;
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
        Password passwordObject = modelsFactory.getPassword(savedAccount, dataHandler.encrypt(password));
        if (!passwordDaoFacade.create(passwordObject)) {
            throw new IllegalStateException();
        }
        return true;
    }

    @Override
    public Account login(String email, String password) {
        Password passwordObject = new Password();
        Account account = new Account();
        account.setEmail(email);
        passwordObject.setAccount(accountDaoFacade.select(account));
        passwordObject.setStringPassword(password);
        passwordObject = passwordDaoFacade.select(passwordObject);
        if (passwordObject.equals(getNullPassword())) {
            throw new IncorrectDataException(IncorrectData.WRONG_EMAIL);
        }
        if (!passwordObject.getStringPassword().equals(dataHandler.encrypt(password))) {
            throw new IncorrectDataException(IncorrectData.WRONG_PASSWORD);
        }
        return accountDaoFacade.select(account);
    }
}
