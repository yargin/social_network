package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.dml.DmlExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.GroupDao;

public interface DbFactory {
    AccountDao getAccountDao();

    GroupDao getGroupDao();

    DmlExecutor getDmlExecutor();
}
