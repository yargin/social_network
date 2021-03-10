package com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters;

import javax.servlet.http.HttpServletRequest;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static java.util.Objects.isNull;

public abstract class AbstractFieldsUpdater {
    protected final HttpServletRequest req;
    protected final String updateFailUrl;
    protected String updateSuccessUrl;
    protected boolean paramsAccepted = true;

    public AbstractFieldsUpdater(HttpServletRequest req, String param, String successUrl) {
        this.req = req;
        String stringRequestedId = req.getParameter(param);
        if (!isNull(stringRequestedId)) {
            setSuccessUrl(successUrl, param, stringRequestedId);
        } else {
            updateSuccessUrl = successUrl;
        }
        updateFailUrl = ACCOUNT_WALL;
    }

    public void setSuccessUrl(String successUrl, String param, String value) {
        updateSuccessUrl = successUrl + '?' + param + '=' + value;
    }

    public boolean isParamsAccepted() {
        return paramsAccepted;
    }
}
