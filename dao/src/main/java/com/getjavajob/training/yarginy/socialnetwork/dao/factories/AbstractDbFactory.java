package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.MySqlDbFactory;

public abstract class AbstractDbFactory {
    public static DbFactory getDbFactory() {
        return new MySqlDbFactory();
    }
}
