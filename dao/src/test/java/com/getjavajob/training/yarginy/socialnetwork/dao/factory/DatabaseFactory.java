package com.getjavajob.training.yarginy.socialnetwork.dao.factory;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.MySqlDbFactory;

/**
 * specifies database factory
 */
public class DatabaseFactory {
    private static final DbFactory DB_FACTORY = new MySqlDbFactory();

    public static DbFactory getDbFactory() {
        return DB_FACTORY;
    }
}
