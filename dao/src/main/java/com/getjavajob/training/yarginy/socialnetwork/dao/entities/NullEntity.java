package com.getjavajob.training.yarginy.socialnetwork.dao.entities;

public class NullEntity implements Entity {
    @Override
    public String getIdentifier() {
        return "";
    }

    @Override
    public int getId() {
        return -1;
    }
}
