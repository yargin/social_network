package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.PasswordDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.repositories.RepoPasswordDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.stereotype.Component;

@Component("passwordDaoFacade")
public class PasswordDaoFacadeImpl implements PasswordDaoFacade {
    private final PasswordDao passwordDao;
    private final TransactionPerformer transactionPerformer;
    private final RepoPasswordDao repoPasswordDao;

    public PasswordDaoFacadeImpl(PasswordDao passwordDao, TransactionPerformer transactionPerformer,
                                 RepoPasswordDao repoPasswordDao) {
        this.passwordDao = passwordDao;
        this.transactionPerformer = transactionPerformer;
        this.repoPasswordDao = repoPasswordDao;
    }

    @Override
    public Password select(Password password) {
        return passwordDao.select(password);
    }

    @Override
    public Password select(long accountId) {
        return passwordDao.select(accountId);
    }

    @Override
    public boolean create(Password password) {
        return transactionPerformer.perform(() -> passwordDao.create(password));
    }

    @Override
    public boolean delete(Password password) {
        return transactionPerformer.perform(() -> passwordDao.delete(password));
    }

    @Override
    public Password getNullPassword() {
        return passwordDao.getNullModel();
    }

    @Override
    public Password getPasswordByAccount(Account account) {
        return repoPasswordDao.getPasswordByAccount(account);
    }
}
