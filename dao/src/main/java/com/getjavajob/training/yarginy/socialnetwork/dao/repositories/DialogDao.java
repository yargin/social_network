package com.getjavajob.training.yarginy.socialnetwork.dao.repositories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;

@Repository("dialogRepo")
public interface DialogDao extends JpaRepository<Dialog, Long>, Serializable {
    Collection<Dialog> findByFirstAccountOrSecondAccount(Account firsAccount, Account secondAccount);

    @Query("select d from Dialog d where d.id = ?1 and (d.firstAccount = ?2 or d.secondAccount = ?3)")
    Dialog selectByIdAndTalker(long id, Account firstAccount, Account secondAccount);

    Dialog findByFirstAccountAndSecondAccount(Account firstAccount, Account secondAccount);
}
