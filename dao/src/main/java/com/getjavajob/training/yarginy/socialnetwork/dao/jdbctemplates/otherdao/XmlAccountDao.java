package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.otherdao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XmlAccountDao {
    private XStream xstream;

    @Autowired
    public XmlAccountDao(XStream xstream) {
        this.xstream = xstream;
    }

    @Autowired
    public void setXstream(XStream xstream) {
        this.xstream = xstream;
    }

    public String toXml(Account account, List<Phone> phones) {
        xstream.processAnnotations(AccountInfo.class);
        xstream.alias("accountInfo" , AccountInfo.class);
        xstream.alias("account" , Account.class);
        xstream.omitField(Account.class, "id");
        xstream.omitField(Account.class, "email");
        xstream.omitField(Account.class, "registrationDate");
        xstream.omitField(Account.class, "version");

        xstream.alias("phone", Phone.class);
        xstream.omitField(Phone.class, "owner");
        xstream.omitField(Phone.class, "id");

        AccountInfo accountInfo = new AccountInfo(account, phones);
        return xstream.toXML(accountInfo);
    }

    public AccountInfo fromXml(String xml) {
        xstream.alias("accountInfo" , AccountInfo.class);
        xstream.alias("account" , Account.class);
        xstream.alias("phone", Phone.class);
        Object object = xstream.fromXML(xml);
        return (AccountInfo) object;
    }
}
