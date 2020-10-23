package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.PhonesDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class BatchPhonesDml extends PhonesDml implements BatchDml<Phone> {
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();

    @Override
    public PreparedStatement batchSelectUpdate(Connection connection, Collection<Phone> entities) throws SQLException {
        StringBuilder numberBuilder = new StringBuilder();
        String select;
        if (entities.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            for (Phone phone : entities) {
                Account account = phone.getOwner();
                account = accountDao.approveFromStorage(account);
                phone.setOwner(account);
                numberBuilder.append(phone.getNumber()).append(", ");
            }
            String numbers = numberBuilder.substring(0, numberBuilder.length() - 2);
            select = buildQuery().select(TABLE).whereIn(new String[]{NUMBER}, new String[]{numbers}).build();
        }
        return connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
}
