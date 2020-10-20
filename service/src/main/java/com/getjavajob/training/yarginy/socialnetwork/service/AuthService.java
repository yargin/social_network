package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.sun.xml.internal.ws.api.pipe.PipelineAssembler;

import java.util.Collection;

public interface AuthService {
    /**
     * @param account
     * @param phones
     * @param password
     * @return
     * @throws
     */
    boolean register(Account account, Collection<Phone> phones, AccountPhoto accountPhoto, Password password);

    boolean register(AccountInfoDTO accountInfoDTO, Password password);

    Account login(String email, String password);

    boolean logout(Account account);

    boolean delete(Account account);
}
