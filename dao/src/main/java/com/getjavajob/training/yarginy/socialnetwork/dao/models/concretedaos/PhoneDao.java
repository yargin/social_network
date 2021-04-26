package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.BatchDelegateDaoTx;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.PhoneDaoTransactional;
import org.springframework.stereotype.Repository;

@Repository("phoneDao")
public class PhoneDao extends BatchDelegateDaoTx<Phone> {
    public PhoneDao(PhoneDaoTransactional phoneDaoTransactional) {
        super(phoneDaoTransactional);
    }
}
