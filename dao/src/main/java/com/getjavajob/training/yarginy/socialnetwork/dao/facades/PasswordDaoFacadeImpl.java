package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.PasswordDao;
import org.springframework.stereotype.Component;

@Component("passwordDaoFacade")
public class PasswordDaoFacadeImpl implements PasswordDaoFacade {
    private final PasswordDao passwordDao;
    private final TransactionPerformer transactionPerformer;

    public PasswordDaoFacadeImpl(PasswordDao passwordDao, TransactionPerformer transactionPerformer) {
        this.passwordDao = passwordDao;
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public Password select(Password password) {
        return passwordDao.select(password);
    }

    @Override
    public boolean create(Password password) {
        return transactionPerformer.transactionPerformed(passwordDao::create, password);
    }

    @Override
    public boolean delete(Password password) {
        return transactionPerformer.transactionPerformed(passwordDao::delete, password);
    }

    @Override
    public Password getNullPassword() {
        return passwordDao.getNullModel();
    }
}
