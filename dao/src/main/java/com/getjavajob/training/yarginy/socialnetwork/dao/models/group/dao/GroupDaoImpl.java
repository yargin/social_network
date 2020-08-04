package com.getjavajob.training.yarginy.socialnetwork.dao.models.group.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractEntityDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.sql.GroupDml;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class GroupDaoImpl extends AbstractEntityDao<Group> implements GroupDao {
    private final GroupDml groupDml;

    public GroupDaoImpl(DbConnector dbConnector, GroupDml groupDml) {
        super(dbConnector, groupDml);
        this.groupDml = groupDml;
    }

    @Override
    public Group getNullEntity() {
        Group nullGroup = new GroupImpl();
        nullGroup.setName("group not found");
        return nullGroup;
    }

    @Override
    public Collection<Account> selectMembers(Group group) {
        try (Connection connection = dbConnector.getConnection()) {
            return groupDml.selectMembers(connection, group);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
