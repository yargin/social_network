package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;

@Repository
public class AccountPhonesDao extends AbstractOneToManyDao<Phone> {
    private static final String ALIAS = "ph";
    private static final String ACC_ALIAS = "acc";
    private final PhoneDao phoneDao;
    private final AccountDao accountDao;

    @Autowired
    public AccountPhonesDao(DataSource dataSource, PhoneDao phoneDao, AccountDao accountDao) {
        super(dataSource);
        this.phoneDao = phoneDao;
        this.accountDao = accountDao;
    }

    private String getSelectManyQuery() {
        return "SELECT " + phoneDao.getViewFields(ALIAS) + ", " + accountDao.getViewFields(ACC_ALIAS) +
                " FROM phones ph JOIN " + accountDao.getTable(ACC_ALIAS) + " ON acc.id = ph.owner_id " +
                "WHERE ph.owner_id = ?";
    }

    @Override
    public Collection<Phone> selectMany(long accountId) {
        String query = getSelectManyQuery();
        return template.query(query, phoneDao.getSuffixedViewRowMapper(ALIAS, ACC_ALIAS), accountId);
    }

    @Override
    protected String getSelectByBothQuery() {
        return "SELECT " + phoneDao.getViewFields(ALIAS) + ", " + accountDao.getViewFields(ACC_ALIAS) +
                " FROM phones ph JOIN " + accountDao.getTable(ACC_ALIAS) + " ON acc.id = ph.owner_id " +
                " WHERE ph.owner_id = ? AND ph.id = ?";
    }

    @Override
    protected Object[] getBothParams(long accountId, long phoneId) {
        return new Object[]{accountId, phoneId};
    }
}
