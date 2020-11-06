package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;

public class GroupManagementAccessHelper {
    private final GroupService groupService = new GroupServiceImpl();

    public boolean isModerator(Group group, Account account) {
        if (Role.ADMIN.equals(account.getRole()) || Objects.equals(group.getOwner(), account)) {
            return true;
        }
        return groupService.isModerator(account.getId(), group.getId());
    }

    public boolean isMember(Group group, Account account) {
        return groupService.isMember(account.getId(), group.getId());
    }

    public Account getAccountFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Account account = new AccountImpl();
        account.setId((long) session.getAttribute(USER_ID));
        account.setEmail((String) session.getAttribute(USER_EMAIL));
        account.setRole((Role) session.getAttribute(USER_ROLE));
        account.setName((String) session.getAttribute(USER_NAME));
        account.setSurname((String) session.getAttribute(USER_SURNAME));
        return account;
    }

    public long getRequestedId(HttpServletRequest req, HttpServletResponse resp, String idParameter) {
        String stringRequestedId = req.getParameter(idParameter);
        long requestedId;
        try {
            requestedId = Long.parseLong(stringRequestedId);
        } catch (NumberFormatException e) {
            return 0;
        }
        if (requestedId < 1) {
            return 0;
        }
        return requestedId;
    }
}
