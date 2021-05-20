package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.GenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullPassword;
import static java.util.Objects.isNull;

@Repository
public class PasswordDao extends GenericDao<Password> {
    @Override
    public Password getNullModel() {
        return getNullPassword();
    }

    @Override
    protected TypedQuery<Password> getSelectByAltKey(Password password) {
        TypedQuery<Password> query = entityManager.createQuery("select p from Password p " +
                "where p.account.email = :email", Password.class);
        query.setParameter("email", password.getAccount().getEmail());
        return query;
    }

    @Override
    public Password selectFullInfo(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Password selectByPk(long id) {
        TypedQuery<Password> query = entityManager.createQuery("select p from Password p " +
                "where p.account.id = :id", Password.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    protected TypedQuery<Password> getSelectAll() {
        return entityManager.createQuery("select p from Password p join fetch p.account a", Password.class);
    }

    @Override
    protected Password getModelReference(Password password) {
        return entityManager.getReference(Password.class, password.getAccount());
    }

    @Override
    protected boolean checkEntityFail(Password password) {
        return isNull(password.getAccount());
    }

    @Override
    protected void prepareModelRelations(Password password) {
        password.setAccount(entityManager.find(Account.class, password.getAccount().getId()));
    }
}
