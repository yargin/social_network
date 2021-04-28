package com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.NewDialogDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.NewOneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class NewAccountDialogsDao implements NewOneToManyDao<Dialog> {
    @PersistenceContext
    private EntityManager entityManager;
    private NewDialogDao dialogDao;

    @Autowired
    public void setDialogDao(NewDialogDao dialogDao) {
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
