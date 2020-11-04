package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;

public class GroupManagementAccessHelper {
    private final GroupService groupService = new GroupServiceImpl();

    public boolean isAbleToManage(Group group, Account account) {
        if (Role.ADMIN.equals(account.getRole()) || Objects.equals(group.getOwner(), account)) {
            return true;
        }
        Collection<Account> moderators = groupService.getModerators(group);
        return moderators.contains(account);
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
}
