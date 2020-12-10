package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneFacade;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    private final AccountFacade accountFacade;
    private final PhoneFacade phoneFacade;
//    private final TransactionManager transactionManager;


    @Autowired
    public AccountInfoServiceImpl(AccountFacade accountFacade, PhoneFacade phoneFacade) {
        this.accountFacade = accountFacade;
        this.phoneFacade = phoneFacade;
    }

    @Override
    public AccountInfoDTO select(Account account) {
        Account storedAccount = accountFacade.select(account);
        Collection<Phone> phones = phoneFacade.selectPhonesByOwner(storedAccount.getId());
        return new AccountInfoDTO(storedAccount, phones);
    }

    @Override
    public AccountInfoDTO select(long id) {
        Account storedAccount = accountFacade.select(id);
        Collection<Phone> phones = phoneFacade.selectPhonesByOwner(storedAccount.getId());
        return new AccountInfoDTO(storedAccount, phones);
    }

    @Override
    public boolean update(AccountInfoDTO accountInfo, AccountInfoDTO storedAccountInfo) {
//        try (Transaction transaction = transactionManager.getTransaction()) {
        Account account = accountInfo.getAccount();
        if (!accountFacade.update(account, storedAccountInfo.getAccount())) {
            throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
        }
        if (!phoneFacade.update(storedAccountInfo.getPhones(), accountInfo.getPhones())) {
            throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
        }
//            transaction.commit();
        return true;
//        } catch (IncorrectDataException e) {
//            throw e;
//        } catch (Exception e) {
//            return false;
//        }
    }
}
