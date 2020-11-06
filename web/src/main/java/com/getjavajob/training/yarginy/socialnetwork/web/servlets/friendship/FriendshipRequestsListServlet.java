package com.getjavajob.training.yarginy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;

public class FriendshipRequestsListServlet extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();
    private final AccountInfoHelper infoHelper = new AccountInfoHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        Collection<Account> requesters = accountService.getFriendshipRequests(requestedId);
        req.setAttribute("requesters", requesters);

        infoHelper.setAccountInfo(req, requestedId);

        req.setAttribute(TAB, "friendshipRequestsList");
        req.getRequestDispatcher(Jsps.FRIENDSHIP_REQUESTS_LIST).forward(req, resp);
    }
}
