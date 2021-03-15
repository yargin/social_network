package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.dialog;

import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;

@Component
public class DialogAccessChecker extends HandlerInterceptorAdapter {
    private final DialogService dialogService;
    private final AccountInfoHelper infoHelper;
    private final Redirector redirector;

    @Autowired
    public DialogAccessChecker(DialogService dialogService, AccountInfoHelper infoHelper, Redirector redirector) {
        this.dialogService = dialogService;
        this.infoHelper = infoHelper;
        this.redirector = redirector;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        long dialogId = (long) req.getAttribute(REQUESTED_ID);
        long currentUserId = (long) req.getSession().getAttribute(USER_ID);

        if (infoHelper.isAdmin(req) || dialogService.isTalker(currentUserId, dialogId)) {
            return true;
        }
        redirector.redirectToReferer(req, res);
        return false;
    }
}
