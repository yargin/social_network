package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.ModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.DialogDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.OneToManyDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class AccountDialogsDao implements OneToManyDao<Dialog> {
    private final DialogDao dialogDao;
    private final ModelsFactory factory;
    @PersistenceContext
    private transient EntityManager entityManager;

    public AccountDialogsDao(DialogDao dialogDao, ModelsFactory modelsFactory) {
        this.dialogDao = dialogDao;
        this.factory = modelsFactory;
    }

    @Override
    @Transactional
    public Collection<Dialog> selectMany(long accountId) {
        Account account = factory.getAccount(accountId);
        TypedQuery<Dialog> selectMany = entityManager.createQuery("select d from Dialog d join fetch d.firstAccount " +
                        "join fetch d.secondAccount where d.firstAccount = :account or d.secondAccount = :account",
                Dialog.class);
        selectMany.setParameter("account", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long dialogId) {
        Dialog dialog = dialogDao.select(dialogId);
        Account firstAccount = dialog.getFirstAccount();
        Account secondAccount = dialog.getSecondAccount();
        return !isNull(firstAccount) && !isNull(secondAccount) && (firstAccount.getId() == accountId ||
                 secondAccount.getId() == accountId);
    }
}
