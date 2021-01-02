package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collection;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Repository
public class FriendshipsDao implements SelfManyToManyDao<Account> {
    private static final String ACC_ALIAS = "acc";
    private static final String TABLE = "friendships";
    private static final String FIRST_ACC_ID = "first_account";
    private static final String SECOND_ACC_ID = "second_account";
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insertTemplate;
    private final AccountDao accountDao;

    @Autowired
    public FriendshipsDao(JdbcTemplate template, SimpleJdbcInsert insertTemplate, AccountDao accountDao) {
        this.template = template;
        this.insertTemplate = insertTemplate;
        insertTemplate.withTableName(TABLE);
        this.accountDao = accountDao;
    }

    @Override
    public Collection<Account> select(long accountId) {
        String query = "SELECT " + accountDao.getViewFields(ACC_ALIAS) + " FROM accounts acc WHERE id IN " +
                "(SELECT first_account acc_id FROM Friendships WHERE second_account = ? UNION " +
                " SELECT second_account acc_id FROM Friendships WHERE first_account = ?)";
        return template.query(query, new Object[]{accountId, accountId}, accountDao.getSuffixedViewRowMapper(ACC_ALIAS));
    }

    @Override
    public boolean relationExists(long firstId, long secondId) {
        String query = "SELECT 1 FROM " + TABLE + " WHERE first_account = ? AND second_account = ?";
        try {
            template.queryForObject(query, new Object[]{min(firstId, secondId), max(firstId, secondId)},
                    (resultSet, i) -> resultSet.getInt(1));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean create(long firstId, long secondId) {
        try {
            return insertTemplate.execute(new MapSqlParameterSource().addValue(FIRST_ACC_ID, min(firstId, secondId),
                    Types.BIGINT).addValue(SECOND_ACC_ID, max(firstId, secondId), Types.BIGINT)) == 1;

        } catch (DuplicateKeyException e) {
            return false;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean delete(long firstId, long secondId) {
        String query = "DELETE FROM friendships fr WHERE fr.first_account = ? AND fr.second_account = ?";
        return template.update(query, min(firstId, secondId), max(firstId, secondId)) == 1;
    }
}
