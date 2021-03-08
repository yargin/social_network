package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ERR;
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

    protected <E> void setObjectFromParam(Consumer<E> setter, String param, Function<String, E> fromParamToValue) {
        String enteredValue = req.getParameter(param);
        if (!isNull(enteredValue) && !enteredValue.isEmpty()) {
            E value = null;
            if (!isNull(fromParamToValue)) {
                value = fromParamToValue.apply(enteredValue);
            }
            setFromParam(setter, param, value);
        }
    }

    protected void setPhotoFromParam(Consumer<byte[]> setter, DataHandler dataHandler, String param) {
        Part imagePart;
        try {
            imagePart = req.getPart(param);
        } catch (IOException | ServletException e) {
            throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
        }
        if (!isNull(imagePart)) {
            try (InputStream inputStream = imagePart.getInputStream()) {
                if (inputStream.available() > 0) {
                    setter.accept(dataHandler.readAvatarPhoto(inputStream));
                }
            } catch (IOException e) {
                throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
            } catch (IncorrectDataException e) {
                paramsAccepted = false;
                req.setAttribute(ERR + param, e.getType().getPropertyKey());
            }
        }
    }

    public void setPhotoFromParam(Consumer<byte[]> setter, MultipartFile photo) {
        if (!photo.isEmpty()) {
            try {
                setter.accept(photo.getBytes());
            } catch (IOException e) {
                throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
            } catch (IncorrectDataException e) {
                paramsAccepted = false;
                req.setAttribute(ERR + "photo", e.getType().getPropertyKey());
            }
        }
    }

    protected void setStringFromParam(Consumer<String> setter, String param) {
        String enteredValue = req.getParameter(param);
        if (!isNull(enteredValue)) {
            setFromParam(setter, param, enteredValue);
        }
    }

    protected <E> void setFromParam(Consumer<E> setter, String param, E value) {
        try {
            setter.accept(value);
        } catch (IncorrectDataException e) {
            paramsAccepted = false;
            req.setAttribute(ERR + param, e.getType().getPropertyKey());
            req.setAttribute(param, value);
        }
    }

    protected <E> void setAttribute(String param, Supplier<E> getter) {
        if (isNull(req.getAttribute(param)) && !isNull(getter.get())) {
            req.setAttribute(param, getter.get());
        }
    }

    public boolean isParamsAccepted() {
        return paramsAccepted;
    }
}
