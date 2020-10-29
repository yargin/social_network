package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.util.Collection;

public interface AccountInfoService {
    AccountInfoDTO select(Account account);

    AccountInfoDTO select(long id);

    boolean update(AccountInfoDTO accountInfoDTO, Collection<Phone> oldPhonesList);
}
