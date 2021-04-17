package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractTxDelegateDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.tx.PasswordDaoTx;
import org.springframework.stereotype.Repository;

@Repository("passwordDao")
public class PasswordDao extends AbstractTxDelegateDao<Password> {
    public PasswordDao(PasswordDaoTx passwordDaoTx) {
        super(passwordDaoTx);
    }
}
