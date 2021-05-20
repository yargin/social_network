package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.GenericDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullDialog;
import static java.util.Objects.isNull;

@Repository
public class DialogDao extends GenericDao<Dialog> {
    @Override
    protected TypedQuery<Dialog> getSelectAll() {
        return entityManager.createQuery("select d from Dialog d", Dialog.class);
    }

    @Override
    @Transactional
    public Dialog selectFullInfo(long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("graph.Dialog.allProperties");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(Dialog.class, id, hints);
    }

    @Override
    protected TypedQuery<Dialog> getSelectByAltKey(Dialog dialog) {
        Account firstAccount = dialog.getFirstAccount();
        Account secondAccount = dialog.getSecondAccount();
        if (isNull(firstAccount) || isNull(secondAccount)) {
            throw new IllegalArgumentException();
        }
        Account greaterAccount;
        Account lowerAccount;
        if (firstAccount.getId() > secondAccount.getId()) {
            greaterAccount = firstAccount;
            lowerAccount = secondAccount;
        } else {
            lowerAccount = firstAccount;
            greaterAccount = secondAccount;
        }

        TypedQuery<Dialog> query = entityManager.createQuery("select d from Dialog d join fetch d.firstAccount f " +
                "join fetch d.secondAccount s where d.firstAccount = :greaterAccount " +
                "and d.secondAccount = :lowerAccount", Dialog.class);
        query.setParameter("greaterAccount", greaterAccount);
        query.setParameter("lowerAccount", lowerAccount);
        return query;
    }

    @Override
    protected boolean checkEntityFail(Dialog dialog) {
        Account firstAccount = dialog.getFirstAccount();
        Account secondAccount = dialog.getSecondAccount();
        if (isNull(firstAccount) || isNull(secondAccount)) {
            return true;
        }
        Account greaterAccount;
        if (firstAccount.getId() < secondAccount.getId()) {
            greaterAccount = secondAccount;
            dialog.setSecondAccount(dialog.getFirstAccount());
            dialog.setFirstAccount(greaterAccount);
        }
        return false;
    }

    @Override
    protected void prepareModelRelations(Dialog dialog) {
        dialog.setFirstAccount(entityManager.getReference(Account.class, dialog.getFirstAccount().getId()));
        dialog.setSecondAccount(entityManager.getReference(Account.class, dialog.getSecondAccount().getId()));
    }

    @Override
    protected Dialog selectByPk(long id) {
        return entityManager.find(Dialog.class, id);
    }

    @Override
    public Dialog getNullModel() {
        return getNullDialog();
    }

    @Override
    protected Dialog getModelReference(Dialog dialog) {
        return entityManager.getReference(Dialog.class, dialog.getId());
    }
}
