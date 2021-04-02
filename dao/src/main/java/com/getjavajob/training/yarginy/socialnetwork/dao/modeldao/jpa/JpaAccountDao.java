package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.function.Function;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullAccount;

@Repository
public class JpaAccountDao extends GenericDao<Account> {
    @Override
    protected Function<EntityManager, Account> getSelectByPkFunction(long id) {
        return entityManager -> entityManager.find(Account.class, id);
    }

    @Override
    protected Function<EntityManager, TypedQuery<Account>> getSelectAllFunction() {
        return entityManager -> entityManager.createQuery("select a from Account a", Account.class);
    }

    @Override
    protected Function<EntityManager, TypedQuery<Account>> getSelectByAltKeyFunction(Account account) {
        return entityManager -> {
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

