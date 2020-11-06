package com.getjavajob.training.yarginy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
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

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;

public class FriendshipRequestCreationServlet extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();
    private final AccountInfoHelper infoHelper = new AccountInfoHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long currentId = (long) req.getSession().getAttribute(Attributes.USER_ID);
        long requesterId = (long) req.getAttribute(Attributes.FIRST_REQUESTED_ID);
        if (currentId != requesterId) {
            redirectToReferer(req, resp);
            return;
        }
        long receiverId = (long) req.getAttribute(Attributes.SECOND_REQUESTED_ID);

        infoHelper.setAccountInfo(req, receiverId);

        if (accountService.isFriend(requesterId, receiverId)) {
            req.setAttribute(Attributes.MESSAGE, "label.alreadyFriends");
        }
        boolean created = false;
        try {
            created = accountService.createFriendshipRequest(requesterId, receiverId);
        } catch (IncorrectDataException e) {
            req.setAttribute(Attributes.ERROR, e.getType().getPropertyKey());
        }
        if (!created) {
            req.setAttribute(Attributes.MESSAGE, "label.requestAlreadySent");
        } else {
            req.setAttribute(Attributes.MESSAGE, "label.requestSent");
        }

        req.setAttribute(TAB, "addFriend");
        req.getRequestDispatcher(Jsps.FRIENDSHIP_REQUEST).forward(req, resp);
    }
}
