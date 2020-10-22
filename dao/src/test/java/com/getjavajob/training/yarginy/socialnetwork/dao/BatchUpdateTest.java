package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.junit.Test;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

public class BatchUpdateTest {
    public static final DbFactory DB_FACTORY = getDbFactory();
    private final Dao<Account> accountDao = DB_FACTORY.getAccountDao();
    private final BatchDao<Phone> phoneDao = DB_FACTORY.getPhoneDao();
    private final OneToManyDao<Account, Phone> accountPhones = DB_FACTORY.getAccountsPhones(accountDao, phoneDao);

    @Test
    public void testCreate() {
        Account account = accountDao.select(1);
        Phone firstPhone = new PhoneImpl("100001", account);
        firstPhone.setType(PhoneType.PRIVATE);
        Phone secondPhone = new PhoneImpl("89218942", account);
        secondPhone.setType(PhoneType.PRIVATE);
        phoneDao.delete(firstPhone);
        phoneDao.delete(secondPhone);
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        phoneDao.create(phones);
        Collection<Phone> allAccountPhones = accountPhones.selectMany(account);
        assertTrue(allAccountPhones.containsAll(phones));
        phoneDao.delete(firstPhone);
        phoneDao.delete(secondPhone);
    }
}
