package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.PhonesDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class BatchPhonesDml extends PhonesDml implements BatchDml<Phone> {
    @Override
    public PreparedStatement batchSelect(Connection connection, Collection<Phone> entities) throws SQLException {
        StringBuilder idBuilder = new StringBuilder();
        StringBuilder numberBuilder = new StringBuilder();
        for (Phone phone : entities) {
            idBuilder.append(phone.getId()).append(", ");
            numberBuilder.append(phone.getNumber()).append(", ");
        }
        String ids = idBuilder.substring(0, idBuilder.length() - 2);
        String numbers = numberBuilder.substring(0, idBuilder.length() - 2);
        String select = buildQuery().select(PhonesTable.TABLE).whereInOpen(new String[]{PhonesTable.ID,
                PhonesTable.NUMBER}, new String[]{ids, numbers}).build();
        return connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
}
