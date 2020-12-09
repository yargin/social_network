package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.viewservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;
import static java.util.Objects.isNull;

public class FriendshipRequestCreationServlet extends AbstractGetServlet {
    private final AccountService accountService = new AccountServiceImpl();
    private final AccountInfoHelper infoHelper = new AccountInfoHelper();

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isNull(req.getAttribute("requester")) || !isNull(req.getAttribute("friend"))) {
            redirectToReferer(req, resp);
            return;
        }

        long currentId = (long) req.getSession().getAttribute(Attributes.USER_ID);
        long requesterId = (long) req.getAttribute(Attributes.REQUESTER_ID);
        if (currentId != requesterId) {
            redirectToReferer(req, resp);
            return;
        }
        long receiverId = (long) req.getAttribute(Attributes.RECEIVER_ID);

        try {
            req.setAttribute("created", accountService.createFriendshipRequest(requesterId, receiverId));
        } catch (IncorrectDataException e) {
            req.setAttribute(Attributes.ERROR, e.getType().getPropertyKey());
        }

        infoHelper.setAccountInfo(req, receiverId);
        req.setAttribute(TAB, "addFriend");
        req.setAttribute(Attributes.REQUESTED_ID, receiverId);
        req.getRequestDispatcher(Jsps.FRIENDSHIP_REQUEST).forward(req, resp);
    }
}
