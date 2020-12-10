package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.DialogMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dialogMessageService")
public class DialogMessageServiceImpl extends AbstractMessageService {
    @Autowired
    public DialogMessageServiceImpl(DialogMessageDao dialogMessageDao) {
        super(dialogMessageDao);
    }
}
