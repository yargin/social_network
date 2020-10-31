package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

public interface AccountInfoService {
    AccountInfoDTO select(Account account);

    AccountInfoDTO select(long id);

    boolean update(AccountInfoDTO accountInfoDTO, AccountInfoDTO storedAccountInfoDTO);
}
