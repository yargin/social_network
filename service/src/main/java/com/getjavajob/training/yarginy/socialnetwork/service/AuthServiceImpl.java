package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneFacade;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandleHelper.encrypt;

@Service("authService")
public class AuthServiceImpl implements AuthService {
    //    private final TransactionManager transactionManager;
    private final AccountFacade accountFacade;
    private final PasswordFacade passwordFacade;
    private final PhoneFacade phoneFacade;

    @Autowired
    public AuthServiceImpl(AccountFacade accountFacade, PasswordFacade passwordFacade, PhoneFacade phoneFacade) {
        this.accountFacade = accountFacade;
        this.passwordFacade = passwordFacade;
        this.phoneFacade = phoneFacade;
    }

    @Override
    public boolean register(AccountInfoDTO accountInfoDTO, Password password) {
        return register(accountInfoDTO.getAccount(), accountInfoDTO.getPhones(), password);
    }

    @Override
    public boolean register(Account account, Collection<Phone> phones, Password password) {
//        try (Transaction transaction = transactionManager.getTransaction()) {
        account.setRegistrationDate(Date.valueOf(LocalDate.now()));
        if (!accountFacade.create(account)) {
            throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
        }
        if (!phoneFacade.create(phones)) {
            throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
        }
        if (!passwordFacade.create(password)) {
            throw new IllegalStateException();
        }
//            transaction.commit();
//        } catch (IncorrectDataException e) {
//            throw e;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return true;
    }

    @Override
    public Account login(String email, String password) {
        Password passwordObject = new PasswordImpl();
        Account account = new AccountImpl();
        account.setEmail(email);
        passwordObject.setAccount(account);
        passwordObject.setPassword(password);
        passwordObject = passwordFacade.select(passwordObject);
        if (passwordObject.equals(getNullPassword())) {
            throw new IncorrectDataException(IncorrectData.WRONG_EMAIL);
        }
        if (!passwordObject.getPassword().equals(encrypt(password))) {
            throw new IncorrectDataException(IncorrectData.WRONG_PASSWORD);
        }
        return passwordObject.getAccount();
    }
}
