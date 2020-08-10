package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.MySqlDbFactory;

/**
 * creates new database vendor bounded abstract factory
 */
public abstract class AbstractDbFactory {
    private AbstractDbFactory() {
    }

    public static DbFactory getDbFactory() {
        return new MySqlDbFactory();
    }
}
