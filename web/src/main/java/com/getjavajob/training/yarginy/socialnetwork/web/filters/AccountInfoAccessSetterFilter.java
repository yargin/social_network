package com.getjavajob.training.yarginy.socialnetwork.web.filters;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class AccountInfoAccessSetterFilter implements Filter {
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        long requestedUserId;
        Object objectId = req.getAttribute(REQUESTED_ID);
        if (isNull(objectId)) {
            requestedUserId = (long) req.getAttribute(SECOND_REQUESTED_ID);
        } else {
            requestedUserId = (long) req.getAttribute(REQUESTED_ID);
        }

        Account account = (Account) session.getAttribute(USER);

        if (account.getId() == requestedUserId) {
            req.setAttribute("owner", true);
        }
        if ((!isNull(account.getRole()) && (Role.ADMIN.equals(account.getRole())))) {
            req.setAttribute("admin", true);
        }
        if (accountService.isFriend(account.getId(), requestedUserId)) {
            req.setAttribute("friend", true);
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
