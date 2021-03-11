package com.getjavajob.training.yarginy.socialnetwork.common.validators;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

public abstract class AbstractValidator implements Validator {
    /**
     * validates optional {@link String} fields
     * trims given {@link String}
     *
     * @param string to check and trim
     * @return checked and trimmed {@link String}, null if was null or empty or only-spaces
     */
    public String stringOptional(String string) {
        if (!isNull(string)) {
            String stringOptional = string.trim();
            if (!stringOptional.isEmpty()) {
                return stringOptional;
            }
        }
        return null;
    }

    /**
     * validates mandatory {@link String} fields
     * trims given {@link String}. Then checks that its not empty, only-spaces or null
     *
     * @param string to check
     * @return given {@link String}
     * @throws IncorrectDataException if given {@link String} is empty, null or only-spaces
     */
    public String stringMandatory(String string) {
        String stringMandatory = stringOptional(string);
        if (isNull(stringMandatory)) {
            throw new IncorrectDataException(IncorrectData.WRONG_STRING);
        }
        return stringMandatory;
    }
}
