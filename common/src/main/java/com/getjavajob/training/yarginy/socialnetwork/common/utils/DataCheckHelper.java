package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataFlowException;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public final class DataCheckHelper {
    private DataCheckHelper() {
    }

    /**
     * used for optional {@link String} fields
     * trims given {@link String}
     *
     * @param string to check and trim
     * @return checked and trimmed {@link String}, null if was null or empty or only-spaces
     */
    public static String stringOptional(String string) {
        if (!isNull(string)) {
            String stringOptional = string.trim();
            if (!stringOptional.isEmpty()) {
                return stringOptional;
            }
        }
        return null;
    }

    /**
     * used for mandatory {@link String} fields
     * trims given {@link String}. Then checks that its not empty, only-spaces or null
     *
     * @param string to check
     * @return given {@link String}
     * @throws IncorrectDataException if given {@link String} is empty, null or only-spaces
     */
    public static String stringMandatory(String string) {
        String stringMandatory = stringOptional(string);
        if (isNull(stringMandatory)) {
            throw new IncorrectDataException("can't be null or empty");
        }
        return stringMandatory;
    }

    /**
     * used for optional e-mail-fields
     * trims given {@link String}. Then checks that given {@link String} is e-mail and sets
     * it to lower case
     *
     * @param email to check
     * @return checked e-mail or null if specified e-mail is null
     * @throws IncorrectDataException if given {@link String} is not an e-mail
     */
    public static String emailOptional(String email) {
        String emailOptional = stringOptional(email);
        if (isNull(emailOptional)) {
            return null;
        }
        return isEmail(emailOptional).toLowerCase();
    }

    /**
     * used for mandatory e-mail-fields
     * trims given {@link String} and checks its not too long. Then checks that given {@link String} is e-mail and sets
     * it to lower case
     *
     * @param email to check
     * @return checked e-mail
     * @throws IncorrectDataException if given {@link String} is empty, null or not an e-mail is empty, null
     *                                or only-spaces
     */
    public static String emailMandatory(String email) {
        String emailMandatory = stringMandatory(email);
        return isEmail(emailMandatory).toLowerCase();
    }

    /**
     * used for optional phone-fields
     * trims given {@link String}. Then checks that given {@link String} is phone or null
     *
     * @param phone to check
     * @return checked phone or null if specified phone is null
     * @throws IncorrectDataException if given {@link String} is empty or null or not phone
     */
    public static String phoneOptional(String phone) {
        String phoneOptional = stringOptional(phone);
        if (isNull(phoneOptional)) {
            return null;
        }
        return isPhone(phoneOptional);
    }

    /**
     * used for mandatory phone-fields
     * trims given {@link String}. Then checks that given {@link String} is phone
     *
     * @param phone to check and handle
     * @return checked and handled phone
     * @throws IncorrectDataException if given {@link String} is empty or null or not a phone
     */
    public static String phoneMandatory(String phone) {
        String phoneMandatory = stringMandatory(phone);
        return isPhone(phoneMandatory);
    }

    /**
     * used for mandatory object-fields
     *
     * @param object object to check for null
     * @param <E>    object's type
     * @return given object if it's not null
     * @throws IncorrectDataException if given object is null
     */
    public static <E> E objectMandatory(E object) {
        if (isNull(object)) {
            throw new IncorrectDataException("can't be null");
        }
        return object;
    }

    private static String isEmail(String email) {
        String symbol = "[a-zA-Z0-9]";
        String regex = symbol + "(\\w" + "[.]?)+" + symbol + '@' + symbol + "(\\w{2,}" + "[.])+" + symbol + "{2,8}";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            throw new IncorrectDataException("wrong email");
        }
        return email;
    }

    private static String isPhone(String phone) {
        String space = "[\\s]?";
        String regex = "[+]?" + space + "\\d{1,4}" + "([\\s-]?\\d+)+";
        String regexWithBraces = "[+]?" + space + "\\d{1,4}" + space + "[(]" + space + "\\d+" + space + "[)]" + space +
                "([\\s-]?\\d+)+";
        Pattern pattern = Pattern.compile(regex);
        Pattern patternWithBraces = Pattern.compile(regexWithBraces);
        if (!pattern.matcher(phone).matches() && !patternWithBraces.matcher(phone).matches()) {
            throw new IncorrectDataException("wrong phone");
        }
        return phone;
    }

    /**
     * checks that id is applicable. 0 means that entity is not saved yet, -1 means that it doesn't exist(nullEntity)
     *
     * @param id to check
     * @return checked id
     */
    public static long checkId(long id) {
        if (id == 0) {
            throw new IncorrectDataFlowException("must be read from storage");
        } else if (id < -1) {
            throw new IncorrectDataException("wrong id");
        }
        return id;
    }
}
