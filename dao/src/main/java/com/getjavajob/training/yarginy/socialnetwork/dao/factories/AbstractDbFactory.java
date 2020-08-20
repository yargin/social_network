package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.H2DbFactory;

import static java.util.Objects.isNull;

/**
 * creates new database vendor bounded abstract factory
 */
public abstract class AbstractDbFactory {
    private static DbFactory dbFactory;

    private AbstractDbFactory() {
    }

    public static DbFactory getDbFactory() {
        if (isNull(dbFactory)) {
            dbFactory = new H2DbFactory();
        }
        return dbFactory;
    }
}
