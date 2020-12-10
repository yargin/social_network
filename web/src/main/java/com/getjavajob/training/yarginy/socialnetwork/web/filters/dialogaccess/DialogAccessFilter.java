package com.getjavajob.training.yarginy.socialnetwork.web.filters.dialogaccess;

import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper.isAdmin;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;

public class DialogAccessFilter extends HttpFilter {
    private DialogService dialogService;

    @Autowired
    public void setDialogService(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @Override
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        long dialogId = (long) req.getAttribute(REQUESTED_ID);
        long currentUserId = (long) req.getSession().getAttribute(USER_ID);

        if (isAdmin(req, res, chain)) {
            return;
        }
        if (dialogService.isTalker(currentUserId, dialogId)) {
            chain.doFilter(req, res);
        } else {
            redirectToReferer(req, res);
        }
    }
}
