package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.GenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullPassword;
import static java.util.Objects.isNull;

@Repository
public class PasswordDao extends GenericDao<Password> {
    @Override
    public Password getNullModel() {
        return getNullPassword();
    }

    @Override
    protected Supplier<TypedQuery<Password>> getSelectByAltKey(EntityManager entityManager, Password password) {
        return () -> {
            TypedQuery<Password> query = entityManager.createQuery("select new Password(a, p.stringPassword) " +
                            "from Password p join Account a on p.email = a.email where p.email =:email",
                    Password.class);
            query.setParameter("email", password.getAccount().getEmail());
            return query;
        };
    }

    @Override
    public Password selectFullInfo(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Supplier<Password> getSelectByPk(EntityManager entityManager, long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Supplier<TypedQuery<Password>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select p from Password p join fetch p.account a", Password.class);
    }

    @Override
    protected Supplier<Password> getModelReference(EntityManager entityManager, Password password) {
        return () -> entityManager.getReference(Password.class, password.getAccount());
    }

    @Override
    protected boolean checkEntityFail(Password password) {
        return isNull(password.getAccount());
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, Password password) {
        password.setAccount(entityManager.getReference(Account.class, password.getAccount().getId()));
    }

    @Override
    public void update(Password password) {
        delete(password);
        create(password);
    }
}
