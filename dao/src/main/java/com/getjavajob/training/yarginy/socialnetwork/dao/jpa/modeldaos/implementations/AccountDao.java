package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.GenericDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullAccount;

@Repository
public class AccountDao extends GenericDao<Account> {
    @Override
    protected Account selectByPk(long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    @Transactional
    public Account selectFullInfo(long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("graph.Account.allProperties");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(Account.class, id, hints);
    }

    @Override
    protected TypedQuery<Account> getSelectAll() {
        return entityManager.createQuery("select a from Account a", Account.class);
    }

    @Override
    protected TypedQuery<Account> getSelectByAltKey(Account account) {
        TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.email = :email",
                Account.class);
        query.setParameter("email", account.getEmail());
        return query;
    }

    @Override
    protected boolean checkEntityFail(Account account) {
        return false;
    }

    @Override
    protected void prepareModelRelations(Account account) {
        //nothing to prepare
    }

    @Override
    public Account getNullModel() {
        return getNullAccount();
    }

    @Override
    protected Account getModelReference(Account account) {
        return entityManager.getReference(Account.class, account.getId());
    }

    @Transactional
    public void setRole(Account account, Role role) {
        try {
            Query query = entityManager.createQuery("update Account a set a.role = :role where a = :acc");
            query.setParameter("role", role);
            query.setParameter("acc", account);
            query.executeUpdate();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
