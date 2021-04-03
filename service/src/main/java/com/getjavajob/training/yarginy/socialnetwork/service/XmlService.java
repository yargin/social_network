package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoXml;

import java.util.Collection;

public interface XmlService {
    String toXml(Account account, Collection<Phone> phones);

    AccountInfoXml fromXml(String xml);
}
