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

@Repository("friendshipRequestsDao")
public class FriendshipsRequestsDao implements ManyToManyDao<Account, Account> {
    private static final String ACC_ALIAS = "acc";
    private static final String TABLE = "friendships_requests";
    private static final String REQUESTER_ID = "requester";
    private static final String RECEIVER_ID = "receiver";
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insertTemplate;
    private final AccountDao accountDao;

    @Autowired
    public FriendshipsRequestsDao(JdbcTemplate template, SimpleJdbcInsert insertTemplate, AccountDao accountDao) {
        this.template = template;
        this.insertTemplate = insertTemplate;
        insertTemplate.withTableName(TABLE);
        this.accountDao = accountDao;
    }

    @Override
    public Collection<Account> selectByFirst(long requesterId) {
        String query = "SELECT " + accountDao.getViewFields(ACC_ALIAS) + " FROM accounts acc JOIN friendships_requests " +
                "fr_r ON acc.id = fr_r.receiver WHERE fr_r.requester = ?";
        return template.query(query, new Object[]{requesterId}, accountDao.getSuffixedViewRowMapper(ACC_ALIAS));
    }

    @Override
    public Collection<Account> selectBySecond(long receiverId) {
        String query = "SELECT " + accountDao.getViewFields(ACC_ALIAS) + " FROM accounts acc JOIN friendships_requests " +
                "fr_r ON acc.id = fr_r.requester WHERE fr_r.receiver = ?";
        return template.query(query, new Object[]{receiverId}, accountDao.getSuffixedViewRowMapper(ACC_ALIAS));
    }

    @Override
    public boolean relationExists(long requesterId, long receiverId) {
        String query = "SELECT 1 FROM friendships_requests fr_r WHERE fr_r.requester = ? AND fr_r.receiver = ?";
        try {
            template.queryForObject(query, new Object[]{requesterId, receiverId}, (resultSet, i) -> resultSet.getInt(1));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean create(long requesterId, long receiverId) {
        try {
            return insertTemplate.execute(new MapSqlParameterSource().addValue(REQUESTER_ID, requesterId, Types.BIGINT).
                    addValue(RECEIVER_ID, receiverId, Types.BIGINT)) == 1;
        } catch (DuplicateKeyException e) {
            return false;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean delete(long requesterId, long receiverId) {
        String query = "DELETE FROM friendships_requests fr_r WHERE fr_r.requester = ? AND fr_r.receiver = ?";
        return template.update(query, requesterId, receiverId) == 1;
    }
}
