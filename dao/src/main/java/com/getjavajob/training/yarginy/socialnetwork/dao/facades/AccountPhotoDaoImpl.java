package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao.OwnedModelDao;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class AccountPhotoDaoImpl implements AccountPhotoDao {
    private final OwnedModelDao<Account, AccountPhoto> accountPhotoDao = getDbFactory().getAccountPhotoDao();

    @Override
    public AccountPhoto select(AccountPhoto accountPhoto) {
        return accountPhotoDao.select(accountPhoto);
    }

    @Override
    public boolean create(AccountPhoto accountPhoto) {
        return accountPhotoDao.create(accountPhoto);
    }

    @Override
    public boolean update(AccountPhoto accountPhoto) {
        return accountPhotoDao.update(accountPhoto);
    }

    @Override
    public boolean delete(AccountPhoto accountPhoto) {
        return accountPhotoDao.delete(accountPhoto);
    }

    @Override
    public AccountPhoto getNullAccountPhoto() {
        return accountPhotoDao.getNullEntity();
    }
}
