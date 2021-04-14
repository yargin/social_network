package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullAccount;

@Repository("jpaAccountDao")
public class JpaAccountDao extends JpaGenericDao<Account> {
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
    protected boolean checkEntity(Account model) {
        return true;
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, Account model) {
        //nothing to prepare
    }

    @Override
    public Account getNullModel() {
        return getNullAccount();
    }

    @Override
    protected Supplier<Account> getModelReference(EntityManager entityManager, Account account) {
        return () -> entityManager.getReference(Account.class, account.getId());
    }
}

