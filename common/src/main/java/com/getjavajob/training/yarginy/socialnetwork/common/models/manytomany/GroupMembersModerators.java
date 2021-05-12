package com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;

import javax.persistence.Entity;

@Entity
public class GroupMembersModerators extends GroupMembership {
    private boolean isModerator;

    public GroupMembersModerators() {
    }

    public GroupMembersModerators(Account account, boolean isModerator) {
        super(account);
        this.isModerator = isModerator;
    }

    public GroupMembersModerators(Long id, String name, String surname, String email, boolean isModerator) {
        Account account = new Account(name, surname, email);
        account.setId(id);
        setAccount(account);
        this.isModerator = isModerator;
    }

    public boolean getModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }
}
