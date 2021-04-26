package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository("accountPhonesDao")
public class AccountPhonesDao extends GenericOneToManyDao<Phone> {
    private Dao<Phone> phoneDao;

    @Autowired
    public void setPhoneDao(@Qualifier("phoneDao") Dao<Phone> phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public Collection<Phone> genericSelectMany(EntityManager entityManager, long accountId) {
        Account account = new Account(accountId);
        TypedQuery<Phone> selectMany = entityManager.createQuery("select p from Phone p join fetch p.owner o " +
                "where p.owner = :owner", Phone.class);
        selectMany.setParameter("owner", account);
        return selectMany.getResultList();
    }

    @Override
    @Transactional
    public boolean relationExists(long accountId, long phoneId) {
        Phone phone = phoneDao.select(phoneId);
        return !isNull(phone.getOwner()) && phone.getOwner().getId() == accountId;
    }
}
