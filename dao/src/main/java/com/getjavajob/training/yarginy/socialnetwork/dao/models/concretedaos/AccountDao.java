package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.DelegateDaoTx;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.AccountDaoTransactional;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDao extends DelegateDaoTx<Account> {
    public AccountDao(AccountDaoTransactional accountDaoTransactional) {
        super(accountDaoTransactional);
    }
}
