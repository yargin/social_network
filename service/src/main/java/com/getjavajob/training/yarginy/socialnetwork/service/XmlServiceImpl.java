package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoXml;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class XmlServiceImpl implements XmlService {
    private XStream xstream;

    @Autowired
    public void setXstream(XStream xstream) {
        this.xstream = xstream;
    }

    public String toXml(Account account, Collection<Phone> phones) {
        xstream.alias("accountInfo" , AccountInfoXml.class);

        xstream.alias("account" , Account.class);
        xstream.omitField(Account.class, "id");
        xstream.omitField(Account.class, "email");
        xstream.omitField(Account.class, "registrationDate");
        xstream.omitField(Account.class, "version");

        xstream.alias("phone", Phone.class);
        xstream.omitField(Phone.class, "owner");
        xstream.omitField(Phone.class, "id");

        AccountInfoXml accountInfoXml = new AccountInfoXml(account, phones);
        return xstream.toXML(accountInfoXml);
    }

    public AccountInfoXml fromXml(String xml) {
        xstream.alias("accountInfo" , AccountInfoXml.class);
        xstream.alias("account" , Account.class);
        xstream.alias("phone", Phone.class);
        Object object = xstream.fromXML(xml);
        return (AccountInfoXml) object;
    }
}
