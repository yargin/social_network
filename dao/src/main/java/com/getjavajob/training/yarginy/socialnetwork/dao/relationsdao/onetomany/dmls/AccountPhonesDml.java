package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.PhonesDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountPhonesDml extends OneToManyDml<Account, Phone> {
    private static final String SELECT_BY_ACCOUNT = buildQuery().selectJoin(PhonesTable.TABLE, AccountsTable.TABLE,
            AccountsTable.ID, PhonesTable.OWNER).where(PhonesTable.OWNER).build();

    @Override
    protected String getSelectByOneQuery() {
        return SELECT_BY_ACCOUNT;
    }

    @Override
    protected Dml<Phone> getManyDml() {
        return new PhonesDml();
    }

    @Override
    protected String getSelectByBothQuery() {
        throw new UnsupportedOperationException();
    }
}