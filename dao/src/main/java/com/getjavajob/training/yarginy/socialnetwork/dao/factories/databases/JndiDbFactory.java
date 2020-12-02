package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.common.datasource.DataSourceHolder;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.CommonDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.DataSourceProxy;

public class JndiDbFactory extends CommonDbFactory {
    public JndiDbFactory() {
        super(new DataSourceProxy(DataSourceHolder.getDataSource()));
    }

    @Override
    protected String getScriptDirectory() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getStartingScript() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean runScriptOnStart() {
        return false;
    }
}
