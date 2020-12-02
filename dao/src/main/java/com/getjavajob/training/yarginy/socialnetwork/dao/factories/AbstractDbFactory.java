package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.JndiDbFactory;

import static java.util.Objects.isNull;

/**
 * provides vendor-bounded abstract database factory
 */
public abstract class AbstractDbFactory {
    private static DbFactory dbFactory = getDbFactory();

    private AbstractDbFactory() {
    }

    public static DbFactory getDbFactory() {
        if (isNull(dbFactory)) {
            dbFactory = new JndiDbFactory();
        }
        return dbFactory;
    }
}
