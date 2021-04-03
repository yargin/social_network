package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoXml;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static java.sql.Date.*;
import static java.time.LocalDate.*;
import static java.util.Arrays.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:xmlTestOverrideSpringConfig.xml"})
public class XmlServiceImplTest {
    @Autowired
    private XmlServiceImpl xmlServiceImpl;

    private final String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<accountInfo>\n" +
            "  <account>\n" +
            "    <name>testXml</name>\n" +
            "    <surname>testXml</surname>\n" +
            "    <birthDate>2000-11-12</birthDate>\n" +
            "  </account>\n" +
            "  <phones>\n" +
            "    <phone>\n" +
            "      <number>testNumber</number>\n" +
            "      <type>PRIVATE</type>\n" +
            "    </phone>\n" +
            "    <phone>\n" +
            "      <number>testNumber2</number>\n" +
            "      <type>PRIVATE</type>\n" +
            "    </phone>\n" +
            "  </phones>\n" +
            "</accountInfo>";
    private final Account account = new Account("testXml", "testXml", "testXml");
    private final Phone phone = new Phone("testNumber", account);
    private final Phone phone2 = new Phone("testNumber2", account);

    @Before
    public void initValues() {
        account.setBirthDate(valueOf(of(2000, 11, 12)));
    }

    @Test
    public void testAccountInfoToXml() {
        Collection<Phone> phones = asList(phone, phone2);
        String xml = xmlServiceImpl.toXml(account, phones);
        assertEquals(expectedXml, xml);
    }

    @Test
    public void testAccountInfoFromXml() {
        AccountInfoXml accountInfo = xmlServiceImpl.fromXml(expectedXml);
        assertEquals(asList(phone, phone2), accountInfo.getPhones());
        Account redAccount = accountInfo.getAccount();
        assertEquals(account.getName(), redAccount.getName());
        assertEquals(account.getBirthDate(), redAccount.getBirthDate());
    }
}
