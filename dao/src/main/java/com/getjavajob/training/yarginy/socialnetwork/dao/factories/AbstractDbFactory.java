package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.H2DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.HerokuJawsDBFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.MySqlDbFactory;

import static java.util.Objects.isNull;

/**
 * provides vendor-bounded abstract database factory
 */
public abstract class AbstractDbFactory {
    private static DbFactory dbFactory;

    private AbstractDbFactory() {
    }

    public static DbFactory getDbFactory() {
        if (isNull(dbFactory)) {
            dbFactory = new MySqlDbFactory();
        }
        return dbFactory;
    }
}
