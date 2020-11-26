package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.GroupDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class BatchGroupDml extends GroupDml implements BatchDml<Group> {
    @Override
    public PreparedStatement batchSelectForInsert(Connection connection, Collection<Group> entities) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public PreparedStatement batchSelectForDelete(Connection connection, Collection<Group> entities) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
