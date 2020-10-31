package com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhotoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao.OwnedEntityDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullAccountPhoto;
import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountPhotoTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountPhotoDml implements OwnedEntityDml<Account, AccountPhoto> {
    private static final String SELECT = buildQuery().select(TABLE).where(OWNER_ID).build();
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();

    public PreparedStatement getSelect(Connection connection, Account owner) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        owner = accountDao.approveFromStorage(owner);
        statement.setLong(1, owner.getId());
        return statement;
    }

    public AccountPhoto getNullEntity() {
        return getNullAccountPhoto();
    }

    public AccountPhoto selectFromRow(ResultSet resultSet, Account owner) throws SQLException {
        Account account = accountDao.approveFromStorage(owner);
        AccountPhoto accountPhoto = new AccountPhotoImpl();
        accountPhoto.setOwner(account);
        try (InputStream inputStream = resultSet.getBinaryStream(PHOTO)) {
            accountPhoto.setPhoto(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
        return accountPhoto;
    }

    public void updateRow(ResultSet resultSet, AccountPhoto accountPhoto, AccountPhoto storedAccountPhoto) throws
            SQLException {
        if (!Objects.equals(storedAccountPhoto.getOwner(), accountPhoto.getOwner())) {
            Account account = accountDao.approveFromStorage(accountPhoto.getOwner());
            resultSet.updateLong(OWNER_ID, account.getId());
        }
        if (!Arrays.equals(storedAccountPhoto.getPhoto(), accountPhoto.getPhoto())) {
            resultSet.updateBytes(PHOTO, accountPhoto.getPhoto());
        }
    }
}
