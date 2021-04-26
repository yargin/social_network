package com.getjavajob.training.yarginy.socialnetwork.service.xml;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoXml;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountInfoXmlServiceImpl implements XmlService<AccountInfoXml> {
    private XStream xstream;

    @Autowired
    public void setXstream(XStream xstream) {
        this.xstream = xstream;
        this.xstream.alias("accountInfo" , AccountInfoXml.class);

        this.xstream.alias("account" , Account.class);
        this.xstream.omitField(Account.class, "id");
        this.xstream.omitField(Account.class, "email");
        this.xstream.omitField(Account.class, "registrationDate");
        this.xstream.omitField(Account.class, "version");
        this.xstream.omitField(Account.class, "role");
        this.xstream.omitField(Account.class, "photo");

        this.xstream.alias("phone", Phone.class);
        this.xstream.omitField(Phone.class, "owner");
        this.xstream.omitField(Phone.class, "id");
        this.xstream.omitField(Phone.class, "version");
    }

    @Override
    public String toXml(AccountInfoXml object) {
        return xstream.toXML(object);
    }

    @Override
    public AccountInfoXml fromXml(String xml) {
        try {
            return (AccountInfoXml) xstream.fromXML(xml);
        } catch (ConversionException e) {
            throw new IllegalArgumentException(e);
        } catch (StreamException | ClassCastException e) {
            throw new IllegalStateException(e);
        }
    }
}
