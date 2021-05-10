package com.getjavajob.training.yarginy.socialnetwork.dao.repositories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;

@Repository
public interface RepoGroupDao extends JpaRepository<Group, Long>, GroupGraphRepository, Serializable {
    Group findByName(String name);

    Collection<Group> findByOwner(Account owner);

    Group findByIdAndOwner(long id, Account owner);
}
