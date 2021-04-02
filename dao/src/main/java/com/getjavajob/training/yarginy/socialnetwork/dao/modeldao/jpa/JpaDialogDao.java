package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Function;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullDialog;

public class JpaDialogDao extends GenericDao<Dialog> {
    @Override
    protected Function<EntityManager, TypedQuery<Dialog>> getSelectAllFunction() {
        return entityManager -> entityManager.createQuery("select d from Dialog d", Dialog.class);
    }

    @Override
    protected Function<EntityManager, TypedQuery<Dialog>> getSelectByAltKeyFunction(Dialog dialog) {
        return entityManager -> {
            TypedQuery<Dialog> query = entityManager.createQuery("select d from Dialog d where (d.firstAccount = :fid " +
                    "and d.secondAccount = :sid) or (d.secondAccount = :fid and d.firstAccount = :sid)", Dialog.class);
            query.setParameter("fid", dialog.getFirstAccount().getId());
            query.setParameter("sid", dialog.getSecondAccount().getId());
            return query;
        };
    }

    @Override
    protected Function<EntityManager, Dialog> getSelectByPkFunction(long id) {
        return entityManager -> entityManager.find(Dialog.class, id);
    }

    @Override
    public Dialog getNullModel() {
        return getNullDialog();
    }
}
