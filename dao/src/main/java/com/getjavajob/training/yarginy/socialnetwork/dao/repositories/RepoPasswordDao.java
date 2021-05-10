package com.getjavajob.training.yarginy.socialnetwork.dao.repositories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoPasswordDao extends JpaRepository<Password, Account> {
    Password getPasswordByAccount(Account account);
}
