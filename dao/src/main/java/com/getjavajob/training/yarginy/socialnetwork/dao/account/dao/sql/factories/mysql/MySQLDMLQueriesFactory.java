package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.mysql;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.AbstractConnectionFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.AbstractDMLQueriesFactory;

public class MySQLDMLQueriesFactory extends AbstractDMLQueriesFactory {
    private static final String DIR_PATH = "./src/main/resources/scripts/MySQL/";

    public MySQLDMLQueriesFactory(AbstractConnectionFactory connectionFactory) {
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
