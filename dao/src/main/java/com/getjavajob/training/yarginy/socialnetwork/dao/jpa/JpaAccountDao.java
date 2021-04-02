package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullAccount;

@Repository
public class JpaAccountDao extends GenericDao<Account> {
    @Override
    protected Supplier<Account> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(Account.class, id);
    }

    @Override
    protected Supplier<TypedQuery<Account>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select a from Account a", Account.class);
    }

    @Override
    protected Supplier<TypedQuery<Account>> getSelectByAltKey(EntityManager entityManager, Account account) {
        return () -> {
            TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.email = :email",
                    Account.class);
            query.setParameter("email", account.getEmail());
            return query;
        };
    }

    @Override
    public Account getNullModel() {
        return getNullAccount();
    }
}

