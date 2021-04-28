package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.NewPasswordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("passwordDaoFacade")
public class PasswordDaoFacadeImpl implements PasswordDaoFacade {
    private NewPasswordDao passwordDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setPasswordDao(NewPasswordDao passwordDao) {
        this.passwordDao = passwordDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
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
