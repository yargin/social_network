package com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups;

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
