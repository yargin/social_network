package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import static java.util.Arrays.asList;

public class AuthServiceTest {
    @Autowired
    public static AccountDaoFacade accountDaoFacade;

    @Test
    public void testRegister() {
        Account account = new AccountImpl("testRegister", "testRegister@test.com");
        account.setSurname("testSurname");
        account.setBirthDate(Date.valueOf(LocalDate.of(2001, 1, 1)));
        account.setSex(Sex.MALE);
        accountDaoFacade.delete(account);
        Phone firstPhone = new PhoneImpl("8921123", account);
        Phone secondPhone = new PhoneImpl("1231211", account);
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        Password password = new PasswordImpl();
        password.setPassword("123qwe123");
        password.setAccount(account);
        //todo
//        AuthService authService = new AuthServiceImpl();
//        boolean registered = authService.register(account, phones, password);
//        assertTrue(registered);
        accountDaoFacade.delete(account);
    }
}
