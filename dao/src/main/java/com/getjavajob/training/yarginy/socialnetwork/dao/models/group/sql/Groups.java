package com.getjavajob.training.yarginy.socialnetwork.dao.models.group.sql;

/**
 * table Groups columns
 */
public interface Groups {
    String TABLE = "_Groups";
    String ID = TABLE + '.' + "ID";
    String NAME = TABLE + '.' + "Name";
    String DESCRIPTION = TABLE + '.' + "Description";
    String OWNER = TABLE + '.' + "Owner_id";
}
