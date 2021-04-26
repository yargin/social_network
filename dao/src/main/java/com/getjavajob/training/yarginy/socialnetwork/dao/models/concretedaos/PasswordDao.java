package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.DelegateDaoTx;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.PasswordDaoTransactional;
import org.springframework.stereotype.Repository;

@Repository("passwordDao")
public class PasswordDao extends DelegateDaoTx<Password> {
    public PasswordDao(PasswordDaoTransactional passwordDaoTransactional) {
        super(passwordDaoTransactional);
    }
}
