package com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetReader {
    private ResultSetReader() {
    }

    /**
     * represents columns names from specified {@link ResultSet}
     *
     * @param resultSet specified {@link ResultSet}
     * @return columns names
     * @throws SQLException
     */
    public static String printColumns(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnNumber = metaData.getColumnCount();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= columnNumber; i++) {
            stringBuilder.append(resultSet.getMetaData().getColumnName(i)).append(' ');
        }
        return stringBuilder.toString();
    }
}
