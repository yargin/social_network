package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogDao")
public class DialogDao implements Dao<Dialog> {
    @Override
    public Dialog select(long id) {
        return null;
    }

    @Override
    public Dialog select(Dialog entityToSelect) {
        return null;
    }

    @Override
    public boolean create(Dialog entity) {
        return false;
    }

    @Override
    public boolean update(Dialog entity, Dialog storedEntity) {
        return false;
    }

    @Override
    public boolean delete(Dialog entity) {
        return false;
    }

    @Override
    public Collection<Dialog> selectAll() {
        return null;
    }

    @Override
    public void checkEntity(Dialog entity) {

    }

    @Override
    public Dialog getNullEntity() {
        return null;
    }

    @Override
    public Dialog approveFromStorage(Dialog entity) {
        return null;
    }
}
