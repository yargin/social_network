package com.getjavajob.training.yarginy.socialnetwork.service.xml;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoXml;
import org.springframework.stereotype.Component;

@Component
public class AccountInfoXmlServiceImpl extends GenericXmlService<AccountInfoXml> {
    @Override
    protected void initXstream() {
        xstream.alias("accountInfo" , AccountInfoXml.class);

        xstream.alias("account" , Account.class);
        xstream.omitField(Account.class, "id");
        xstream.omitField(Account.class, "email");
        xstream.omitField(Account.class, "registrationDate");
        xstream.omitField(Account.class, "version");
        xstream.omitField(Account.class, "role");
        xstream.omitField(Account.class, "photo");

        xstream.alias("phone", Phone.class);
        xstream.omitField(Phone.class, "owner");
        xstream.omitField(Phone.class, "id");
        xstream.omitField(Phone.class, "version");
    }
}
