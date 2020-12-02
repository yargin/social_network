package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.PhonesDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable.NUMBER;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable.TABLE;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class BatchPhonesDml extends PhonesDml implements BatchDml<Phone> {
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();

    @Override
    public PreparedStatement batchSelectForInsert(Connection connection, Collection<Phone> phones) throws SQLException {
        StringBuilder numberBuilder = new StringBuilder();
        String select;
        if (phones.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            Account account = null;
            for (Phone phone : phones) {
                if (!Objects.equals(account, phone.getOwner())) {
                    account = accountDao.approveFromStorage(phone.getOwner());
                }
                phone.setOwner(account);
                numberBuilder.append(phone.getNumber()).append(", ");
            }
            String numbers = numberBuilder.substring(0, numberBuilder.length() - 2);
            select = buildQuery().selectAll(TABLE).whereIn(new String[]{NUMBER}, new String[]{numbers}).build();
        }
        return connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    @Override
    public PreparedStatement batchSelectForDelete(Connection connection, Collection<Phone> phones)
            throws SQLException {
        StringBuilder numberBuilder = new StringBuilder();
        String select;
        if (phones.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            for (Phone phone : phones) {
                String number = phone.getNumber();
                numberBuilder.append(number).append(", ");
            }
            String numbers = numberBuilder.substring(0, numberBuilder.length() - 2);
            select = buildQuery().selectAll(TABLE).whereIn(new String[]{NUMBER}, new String[]{numbers}).build();
        }
        return connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
}
