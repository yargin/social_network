package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.viewservlets;

import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;

public class FriendsListServlet extends AbstractGetServlet {
    private AccountService accountService;
    private AccountInfoHelper infoHelper;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setInfoHelper(AccountInfoHelper infoHelper) {
        this.infoHelper = infoHelper;
    }

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);

        req.setAttribute("friends", accountService.getFriends(requestedId));

//        infoHelper.setAccountInfo(req, requestedId);
        req.setAttribute(TAB, "friendsList");
        req.getRequestDispatcher(Jsps.FRIENDS_LIST).forward(req, resp);
    }
}
