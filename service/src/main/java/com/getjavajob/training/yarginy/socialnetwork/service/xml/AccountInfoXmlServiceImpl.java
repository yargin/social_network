package com.getjavajob.training.yarginy.socialnetwork.service.xml;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoXml;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountInfoXmlServiceImpl extends GenericXmlService<AccountInfoXml> {
    @Autowired
    public void setXstream(XStream xstream) {
        this.xstream = xstream;
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
    }
}
