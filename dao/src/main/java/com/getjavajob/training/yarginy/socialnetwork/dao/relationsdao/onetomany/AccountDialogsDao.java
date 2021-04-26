package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository("accountDialogsDao")
public class AccountDialogsDao extends GenericOneToManyDao<Dialog> {
    private Dao<Dialog> dialogDao;

    @Autowired
    public void setDialogDao(@Qualifier("dialogDao") Dao<Dialog> dialogDao) {
        this.dialogDao = dialogDao;
    }

    @Override
    public Collection<Dialog> genericSelectMany(EntityManager entityManager, long accountId) {
        Account account = new Account(accountId);
        TypedQuery<Dialog> selectMany = entityManager.createQuery("select d from Dialog d join fetch d.firstAccount f " +
                "join fetch d.secondAccount s where d.firstAccount = :account or d.secondAccount = :account",
                Dialog.class);
        selectMany.setParameter("account", account);
        return selectMany.getResultList();
    }

    @Override
    @Transactional
    public boolean relationExists(long accountId, long dialogId) {
        Dialog dialog = dialogDao.select(dialogId);
        return !isNull(dialog.getFirstAccount()) && dialog.getFirstAccount().getId() == accountId ||
                 !isNull(dialog.getSecondAccount()) && dialog.getSecondAccount().getId() == accountId;
    }
}
