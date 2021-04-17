package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractTxDelegateDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.tx.AccountDaoTx;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDao extends AbstractTxDelegateDao<Account> {
    public AccountDao(AccountDaoTx accountDaoTx) {
        super(accountDaoTx);
    }
}
