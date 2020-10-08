package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.accountsphones;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.PhonesDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountsPhonesDml extends OneToManyDml<Account, Phone> {
    private static final String SELECT_BY_ACCOUNT = buildQuery().selectJoin(AccountsTable.TABLE, PhonesTable.TABLE,
            AccountsTable.ID, PhonesTable.OWNER).where(PhonesTable.OWNER).build();

    @Override
    protected String getSelectQueryByOne() {
        return SELECT_BY_ACCOUNT;
    }

    @Override
    protected Dml<Phone> getManyDml() {
        return new PhonesDml();
    }
}