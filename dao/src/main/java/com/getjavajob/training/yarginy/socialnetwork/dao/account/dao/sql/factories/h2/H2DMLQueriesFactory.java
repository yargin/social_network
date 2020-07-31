package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.h2;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.AbstractConnectionFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.AbstractDMLQueriesFactory;

public class H2DMLQueriesFactory extends AbstractDMLQueriesFactory {
    private static final String DIR_PATH = "./src/main/resources/scripts/H2/";

    public H2DMLQueriesFactory(AbstractConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected String getCreateAccountsQueryFile() {
        return DIR_PATH + "create_table_accounts";
    }

    @Override
    protected String getDropAccountsQueryFile() {
        return DIR_PATH + "drop_table_accounts";
    }
}
