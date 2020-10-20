package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static com.getjavajob.training.yarginy.socialnetwork.service.utils.TestResultPrinter.printPassed;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

public class AuthServiceTest {
    public static final String CLASS = "AuthServiceTest";
    public static final DbFactory DB_FACTORY = getDbFactory();
    public static final Dao<Account> ACCOUNT_DAO = DB_FACTORY.getAccountDao();

    @Test
    public void testRegister() {
        Account account = new AccountImpl("testRegister", "testRegister@test.com");
        account.setSurname("testSurname");
        account.setBirthDate(LocalDate.of(2001, 1, 1));
        account.setSex(Sex.MALE);
        ACCOUNT_DAO.delete(account);
        Phone firstPhone = new PhoneImpl("8921123", account);
        Phone secondPhone = new PhoneImpl("1231211", account);
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        Password password = new PasswordImpl();
        password.setPassword("123qwe123");
        password.setAccount(account);
        AuthService authService = new AuthServiceImpl();
        boolean registered = authService.register(account, phones, null, password);
        assertTrue(registered);
        ACCOUNT_DAO.delete(account);
        printPassed(CLASS, "testRegister");
    }
}
