package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.PhoneDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class AccountPhonesDao implements OneToManyDao<Phone> {
    private static final String ALIAS = "ph";
    private static final String ACC_ALIAS = "acc";
    private final PhoneDao phoneDao;
    private final AccountDao accountDao;
    private final transient JdbcTemplate template;

    public AccountPhonesDao(JdbcTemplate template, PhoneDao phoneDao, AccountDao accountDao) {
        this.phoneDao = phoneDao;
        this.accountDao = accountDao;
        this.template = template;
    }

    private String getSelectManyQuery() {
        return "SELECT " + phoneDao.getViewFields(ALIAS) + ", " + accountDao.getViewFields(ACC_ALIAS) +
                " FROM phones ph JOIN " + accountDao.getTable(ACC_ALIAS) + " ON acc.id = ph.owner_id " +
                "WHERE ph.owner_id = ?";
    }

    private String getSelectByBothQuery() {
        return "SELECT " + phoneDao.getViewFields(ALIAS) + ", " + accountDao.getViewFields(ACC_ALIAS) +
                " FROM phones ph JOIN " + accountDao.getTable(ACC_ALIAS) + " ON acc.id = ph.owner_id " +
                " WHERE ph.owner_id = ? AND ph.id = ?";
    }

    @Override
    public Collection<Phone> selectMany(long accountId) {
        String query = getSelectManyQuery();
        return template.query(query, phoneDao.getSuffixedViewRowMapper(ALIAS), accountId);
    }

    @Override
    public boolean relationExists(long accountId, long phoneId) {
        String query = getSelectByBothQuery();
        try {
            template.queryForObject(query, new Object[]{accountId, phoneId}, ((resultSet, i) -> resultSet.getInt(1)));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
