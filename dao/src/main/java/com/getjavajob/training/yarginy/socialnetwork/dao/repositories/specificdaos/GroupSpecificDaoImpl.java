package com.getjavajob.training.yarginy.socialnetwork.dao.repositories.specificdaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

public class GroupSpecificDaoImpl implements GroupSpecificDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Group getFullInfo(long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("graph.Group.allProperties");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(Group.class, id, hints);
    }
}
