package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullDialog;

public class JpaDialogDao extends GenericDao<Dialog> {
    @Override
    protected Supplier<TypedQuery<Dialog>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select d from Dialog d", Dialog.class);
    }

    @Override
    protected Supplier<TypedQuery<Dialog>> getSelectByAltKey(EntityManager entityManager, Dialog dialog) {
        return () -> {
            TypedQuery<Dialog> query = entityManager.createQuery("select d from Dialog d where (d.firstAccount = :fid " +
                    "and d.secondAccount = :sid) or (d.secondAccount = :fid and d.firstAccount = :sid)", Dialog.class);
            query.setParameter("fid", dialog.getFirstAccount().getId());
            query.setParameter("sid", dialog.getSecondAccount().getId());
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
}
