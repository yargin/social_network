package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class UpdateFieldsHelper {
    /**
     * gets class E object from request parameter & sets it into model object
     *
     * @param setter           puts received value into model-object
     * @param param            request parameter's name to receive value
     * @param req              request with parameter
     * @param paramsAccepted   flag that value was successfully set
     * @param fromParamToValue transforms string parameter into applicable object
     * @param <E>              value's type
     */
    protected <E> void setObjectFromParam(Consumer<E> setter, String param, HttpServletRequest req,
                                          ThreadLocal<Boolean> paramsAccepted, Function<String, E> fromParamToValue) {
        String enteredValue = req.getParameter(param);
        if (!isNull(enteredValue) && !enteredValue.isEmpty()) {
            E value = null;
            if (!isNull(fromParamToValue)) {
                value = fromParamToValue.apply(enteredValue);
            }
            setFromParam(setter, param, req, paramsAccepted, value);
        }
    }

    protected void setPhotoFromParam(Consumer<InputStream> setter, String param, HttpServletRequest req,
                                     ThreadLocal<Boolean> paramsAccepted) throws IOException, ServletException {
        Part imagePart = req.getPart(param);
        if (!isNull(imagePart)) {
            try (InputStream inputStream = imagePart.getInputStream()) {
                if (inputStream.available() > 0) {
                    setter.accept(inputStream);
                }
            } catch (IOException e) {
                throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
            } catch (IncorrectDataException e) {
                paramsAccepted.set(false);
                req.setAttribute(ERR + param, e.getType().getPropertyKey());
            }
        }
    }

    protected void setStringFromParam(Consumer<String> setter, String param, HttpServletRequest req,
                                      ThreadLocal<Boolean> paramsAccepted) {
        String enteredValue = req.getParameter(param);
        if (!isNull(enteredValue)) {
            setFromParam(setter, param, req, paramsAccepted, enteredValue);
        }
    }

    protected <E> void setFromParam(Consumer<E> setter, String param, HttpServletRequest req,
                                    ThreadLocal<Boolean> paramsAccepted, E value) {
        try {
            setter.accept(value);
        } catch (IncorrectDataException e) {
            req.setAttribute(ERR + param, e.getType().getPropertyKey());
            paramsAccepted.set(false);
            req.setAttribute(param, value);
        }
    }

    protected <E> void setAttribute(HttpServletRequest req, String param, Supplier<E> getter) {
        if (isNull(req.getAttribute(param)) && !isNull(getter.get())) {
            req.setAttribute(param, getter.get());
        }
    }

    public Account getAccountFromSession(HttpServletRequest req) {
        Account account = new AccountImpl();
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute(USER_NAME);
        account.setName(name);
        Role role = (Role) session.getAttribute(USER_ROLE);
        account.setRole(role);
        String email = (String) session.getAttribute(USER_EMAIL);
        account.setEmail(email);
        String surname = (String) session.getAttribute(USER_SURNAME);
        account.setSurname(surname);
        long id = (long) session.getAttribute(USER_ID);
        account.setId(id);
        return account;
    }

    public long getRequestedUserId(HttpServletRequest req, HttpServletResponse resp, String idParameter) throws IOException {
        String stringRequestedId = req.getParameter(idParameter);
        long requestedUserId;
        try {
            requestedUserId = Long.parseLong(stringRequestedId);
        } catch (NumberFormatException e) {
            long sessionId = (long) req.getSession().getAttribute(USER_ID);
            RedirectHelper.redirect(req, resp, Pages.MY_WALL, USER_ID, "" + sessionId);
            return 0;
        }
        if (requestedUserId < 1) {
            long sessionId = (long) req.getSession().getAttribute(USER_ID);
            RedirectHelper.redirect(req, resp, Pages.MY_WALL, USER_ID, "" + sessionId);
            return 0;
        }
        return requestedUserId;
    }
}
