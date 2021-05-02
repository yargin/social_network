package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
    @Mock
    private AccountDaoFacade accountDaoFacade;
    @Mock
    private PasswordDaoFacade passwordDaoFacade;
    @Mock
    private PhoneDaoFacade phoneDaoFacade;
    @Mock
    private DataHandler dataHandler;
    @InjectMocks
    private AuthServiceImpl authService;

    //todo
//    @Test
//    public void testRegister() {
//        Account account = new Account("testRegister", "testRegister@test.com");
//        account.setSurname("testSurname");
//        account.setBirthDate(Date.valueOf(LocalDate.of(2001, 1, 1)));
//        account.setSex(Sex.MALE);
//        Phone firstPhone = new Phone("8921123", account);
//        Phone secondPhone = new Phone("1231211", account);
//        Collection<Phone> phones = asList(firstPhone, secondPhone);
//        when(accountDaoFacade.create(account)).thenReturn(true);
//        when(accountDaoFacade.select(account)).thenReturn(account);
//        when(phoneDaoFacade.create(phones)).thenReturn(true);
//        when(passwordDaoFacade.create(new Password(account, "123qwe123"))).thenReturn(true);
//        boolean registered = authService.register(account, phones, "123qwe123");
//        assertTrue(registered);
//    }
}
