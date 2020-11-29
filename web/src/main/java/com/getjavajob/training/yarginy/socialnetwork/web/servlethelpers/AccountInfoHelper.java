package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

public class AccountInfoHelper {
    private static final AccountService ACCOUNT_SERVICE = new AccountServiceImpl();

    public void setAccountInfo(HttpServletRequest req, long requestedUserId) {
        AccountInfoDTO accountInfoDTO = ACCOUNT_SERVICE.getAccountInfo(requestedUserId);

        Account account = accountInfoDTO.getAccount();
        req.setAttribute("user", account);

        Collection<Phone> phones = accountInfoDTO.getPhones();
        Collection<Phone> privatePhones = phones.stream().filter(phone -> phone.getType() == PhoneType.PRIVATE).
                collect(Collectors.toList());
        req.setAttribute("privatePhones", privatePhones);
        Collection<Phone> workPhones = phones.stream().filter(phone -> phone.getType() == PhoneType.WORK).
                collect(Collectors.toList());
        req.setAttribute("workPhones", workPhones);
    }
}
