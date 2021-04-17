package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.BatchAbstractTxDelegateDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.tx.PhoneDaoTx;
import org.springframework.stereotype.Repository;

@Repository("phoneDao")
public class PhoneDao extends BatchAbstractTxDelegateDao<Phone> {
    public PhoneDao(PhoneDaoTx phoneDaoTx) {
        super(phoneDaoTx);
    }
}
