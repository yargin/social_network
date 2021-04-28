package com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.NewPhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.NewOneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class NewAccountPhonesDao implements NewOneToManyDao<Phone> {
    @PersistenceContext
    private EntityManager entityManager;
    private NewPhoneDao phoneDao;

    @Autowired
    public void setPhoneDao(NewPhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    @Transactional
    public Collection<Phone> selectMany(long accountId) {
        Account account = new Account(accountId);
        TypedQuery<Phone> selectMany = entityManager.createQuery("select p from Phone p join fetch p.owner o " +
                "where p.owner = :owner", Phone.class);
        selectMany.setParameter("owner", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long phoneId) {
        Phone phone = phoneDao.select(phoneId);
        return !isNull(phone.getOwner()) && phone.getOwner().getId() == accountId;
    }
}
