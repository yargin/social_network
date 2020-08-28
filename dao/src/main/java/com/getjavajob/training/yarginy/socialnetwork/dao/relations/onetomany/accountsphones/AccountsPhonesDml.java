package com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany.accountsphones;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.phones.PhonesDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.phones.PhonesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany.OneToManyDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountsPhonesDml extends OneToManyDml<Account, Phone> {
    public static final String SELECT_BY_ACCOUNT = buildQuery().selectJoin(AccountsTable.TABLE, PhonesTable.TABLE,
            AccountsTable.ID, PhonesTable.OWNER).where(PhonesTable.OWNER).build();
    public static final String SELECT_BY_PHONE = buildQuery().selectJoin(AccountsTable.TABLE, PhonesTable.TABLE,
            AccountsTable.ID, PhonesTable.OWNER).where(PhonesTable.ID).build();

    @Override
    public Collection<Phone> selectByOne(Connection connection, long oneId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ACCOUNT)) {
            statement.setLong(1, oneId);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDml<Phone> phonesDml = new PhonesDml();
                return phonesDml.selectEntities(resultSet);
            }
        }
    }

    @Override
    public Account selectByOneOfMany(Connection connection, long oneOfManyId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_PHONE)) {
            statement.setLong(1, oneOfManyId);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDml<Account> accountDml = new AccountDml();
                if (resultSet.next()) {
                    return accountDml.selectFromRow(resultSet);
                } else {
                    return accountDml.getNullEntity();
                }
            }
        }
    }
}