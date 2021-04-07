package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullDialog;

@Repository
public class JpaDialogDao extends JpaGenericDao<Dialog> {
    @Override
    protected Supplier<TypedQuery<Dialog>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select d from Dialog d", Dialog.class);
    }

    @Override
    protected Supplier<TypedQuery<Dialog>> getSelectByAltKey(EntityManager entityManager, Dialog dialog) {
        return () -> {
            TypedQuery<Dialog> query = entityManager.createQuery("select d from Dialog d where (d.firstAccount = :fid " +
                    "and d.secondAccount = :sid) or (d.secondAccount = :fid and d.firstAccount = :sid)", Dialog.class);
            query.setParameter("fid", dialog.getFirstAccount());
            query.setParameter("sid", dialog.getSecondAccount());
            return query;
        };
    }

    @Override
    protected Supplier<Dialog> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(Dialog.class, id);
    }

    @Override
    public Dialog getNullModel() {
        return getNullDialog();
    }

    @Override
    protected Supplier<Dialog> getModelReference(EntityManager entityManager, Dialog dialog) {
        return () -> entityManager.getReference(Dialog.class, dialog.getId());
    }
}
