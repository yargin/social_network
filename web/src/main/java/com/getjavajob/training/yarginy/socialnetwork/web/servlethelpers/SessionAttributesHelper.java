package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;

public final class SessionAttributesHelper {
    private SessionAttributesHelper() {
    }

    public static Account getAccountFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Account account = new AccountImpl();
        account.setName((String) session.getAttribute(USER_NAME));
        account.setId((long) session.getAttribute(USER_ID));
        Role role = Role.valueOf((String) session.getAttribute(USER_ROLE));
        account.setRole(role);
        account.setEmail((String) session.getAttribute(USER_EMAIL));
        account.setSurname((String) session.getAttribute(USER_SURNAME));
        return account;
    }
}
