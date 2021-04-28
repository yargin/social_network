package com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.DialogDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.OneToManyDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class AccountDialogsDao implements OneToManyDao<Dialog> {
    @PersistenceContext
    private transient EntityManager entityManager;
    private final DialogDao dialogDao;

    public AccountDialogsDao(DialogDao dialogDao) {
        this.dialogDao = dialogDao;
    }

    @Override
    @Transactional
    public Collection<Dialog> selectMany(long accountId) {
        Account account = new Account(accountId);
        TypedQuery<Dialog> selectMany = entityManager.createQuery("select d from Dialog d join fetch d.firstAccount f " +
                        "join fetch d.secondAccount s where d.firstAccount = :account or d.secondAccount = :account",
                Dialog.class);
        selectMany.setParameter("account", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long dialogId) {
        Dialog dialog = dialogDao.select(dialogId);
        return !isNull(dialog.getFirstAccount()) && dialog.getFirstAccount().getId() == accountId ||
                !isNull(dialog.getSecondAccount()) && dialog.getSecondAccount().getId() == accountId;
    }
}
