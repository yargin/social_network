package com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;

import javax.persistence.Entity;

@Entity
public class UnJoinedGroups extends GroupMembership {
    private boolean requestIsSent;

    public UnJoinedGroups() {
    }

    public UnJoinedGroups(Group group, boolean requestIsSent) {
        super(group);
        this.requestIsSent = requestIsSent;
    }

    public boolean isRequestIsSent() {
        return requestIsSent;
    }

    public void setRequestIsSent(boolean requestIsSent) {
        this.requestIsSent = requestIsSent;
    }
}
