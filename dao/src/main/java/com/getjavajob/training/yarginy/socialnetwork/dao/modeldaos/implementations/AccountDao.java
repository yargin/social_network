package com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.GenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullAccount;

@Repository
public class AccountDao extends GenericDao<Account> {
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
    protected boolean checkEntity(Account account) {
        return false;
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, Account account) {
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
