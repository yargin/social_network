package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaPhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class JpaAccountPhones implements JpaOneToManyDao<Phone> {
    private EntityManagerFactory entityManagerFactory;
    private JpaPhoneDao phoneDao;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Autowired
    public void setPhoneDao(JpaPhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public Collection<Phone> selectMany(long accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Account account = new Account(accountId);
        TypedQuery<Phone> selectMany = entityManager.createQuery("select p from Phone p " +
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
