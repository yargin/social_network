package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.password;

import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImpl;

import java.util.Collection;

public class PasswordDao extends DaoImpl<Password> {
    public PasswordDao(ConnectionPool connectionPool, AbstractDml<Password> passwordDml) {
        super(connectionPool, passwordDml);
    }


    @Override
    public boolean delete(Password entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Password select(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Password> selectAll() {
        throw new UnsupportedOperationException();
    }
}
