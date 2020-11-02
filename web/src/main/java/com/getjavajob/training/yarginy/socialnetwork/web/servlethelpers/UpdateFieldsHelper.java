package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ERR;
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
    public static <E> void setObjectFromParam(Consumer<E> setter, String param,
                                              HttpServletRequest req, ThreadLocal<Boolean> paramsAccepted,
                                              Function<String, E> fromParamToValue) {
        String enteredValue = req.getParameter(param);
        if (!isNull(enteredValue)) {
            E value = null;
            if (!isNull(fromParamToValue)) {
                value = fromParamToValue.apply(enteredValue);
            }
            setFromParam(setter, param, req, paramsAccepted, value);
        }
    }

    public static void setPhotoFromParam(HttpServletRequest req, Consumer<InputStream> setter, String param,
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

    private static void setStringFromParam(Consumer<String> setter, String param, HttpServletRequest req,
                                           ThreadLocal<Boolean> paramsAccepted) {
        String enteredValue = req.getParameter(param);
        if (!isNull(enteredValue)) {
            setFromParam(setter, param, req, paramsAccepted, enteredValue);
        }
    }

    private static <E> void setFromParam(Consumer<E> setter, String param, HttpServletRequest req,
                                         ThreadLocal<Boolean> paramsAccepted, E value) {
        try {
            setter.accept(value);
        } catch (IncorrectDataException e) {
            req.setAttribute(ERR + param, e.getType().getPropertyKey());
            paramsAccepted.set(false);
            req.setAttribute(param, value);
        }
    }
}
