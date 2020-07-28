package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Builder;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.EntityFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity.Account;

public interface AccountFactory extends EntityFactory<Account> {
    @Override
    Account getInstance();

    @Override
    Builder<Account> getBuilder();
}
