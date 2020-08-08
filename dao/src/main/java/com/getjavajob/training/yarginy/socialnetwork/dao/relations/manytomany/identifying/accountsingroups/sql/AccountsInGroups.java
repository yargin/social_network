package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.identifying.accountsingroups.sql;

/**
 * table Accounts_in_groups columns
 */
public interface AccountsInGroups {
    String TABLE = "Accounts_in_groups";
    String ACCOUNT_ID = TABLE + '.' + "Account_id";
    String GROUP_ID = TABLE + '.' + "Group_id";
}
