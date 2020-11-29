package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.io.Serializable;

public interface AccountInfoService extends Serializable {
    AccountInfoDTO select(Account account);

    AccountInfoDTO select(long id);

    boolean update(AccountInfoDTO accountInfoDTO, AccountInfoDTO storedAccountInfoDTO);
}
