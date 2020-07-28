package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity;

import com.getjavajob.training.yarginy.socialnetwork.dao.dbutils.ConnectionFactory;

import java.sql.*;

public class Field<E> {
    String fieldName;
    E data;
    ColumnGetter<E> getter;
    ColumnSetter<E> setter;

    public Field(String fieldName, ColumnGetter<E> getter, ColumnSetter<E> setter) {
        this.fieldName = fieldName;
        this.getter = getter;
        this.setter = setter;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.prepareStatement("");
        ResultSet resultSet = statement.getResultSet();
        Field<Integer> field1 = new Field<>("Id", resultSet::getInt, resultSet::updateInt);
        field1.getter.get("");
        field1.setter.set("", field1.data);
    }
}

interface ColumnGetter<E> {
    E get(String column) throws SQLException;
}

interface ColumnSetter<E> {
    void set(String str, E data) throws SQLException;
}
