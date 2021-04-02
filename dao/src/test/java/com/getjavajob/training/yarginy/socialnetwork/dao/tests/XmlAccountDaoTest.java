package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.otherdao.AccountInfo;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.otherdao.XmlAccountDao;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestH2OverrideSpringConfig.xml"})
public class XmlAccountDaoTest {
    @Autowired
    private XmlAccountDao xmlAccountDao;
    private String xml;

    @Test
    public void testAccountInfoToXml() {
        Account account = new Account("testXml", "testXml", "testXml");
        account.setBirthDate(Date.valueOf(LocalDate.of(2000, 11, 12)));
        Phone phone = new Phone("testNumber", account);
        Phone phone2 = new Phone("testNumber2", account);
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        phones.add(phone2);
        xml = xmlAccountDao.toXml(account, phones);
        System.out.println(xml);

        AccountInfo accountInfo = xmlAccountDao.fromXml(xml);
        System.out.println(accountInfo.getAccount());
        System.out.println(accountInfo.getPhones());
    }

//    @Test
//    public void testAccountInfoFromXml() {
//        XmlAccountDao xmlAccountDao = new XmlAccountDao();
//        AccountInfo accountInfo = xmlAccountDao.fromXml(xml);
//        System.out.println(accountInfo.getAccount());
//        System.out.println(accountInfo.getPhones());
//    }
}
