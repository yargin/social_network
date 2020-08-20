package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class DataChecker {
    private DataChecker() {
    }

    /**
     * used for optional {@link String} fields
     * handles given {@link String} - trims leading-tailing spaces
     *
     * @param string to handle
     * @return handled {@link String}
     */
    public static String stringTrim(String string) {
        if (!isNull(string)) {
            string = string.trim();
        }
        return string;
    }

    /**
     * used for mandatory {@link String} fields
     * trims leading-tailing spaces. Then checks that given {@link String} is not null or empty
     *
     * @param string to check
     * @return given {@link String}
     * @throws IncorrectDataException if given {@link String} is empty or null
     */
    public static String stringCheck(String string) {
        stringTrim(string);
        if (isNull(string) || string.isEmpty()) {
            throw new IncorrectDataException("can't be null or empty");
        }
        return string;
    }

    /**
     * used for optional e-mail-fields
     * checks that given {@link String} is e-mail or null and sets it to lower case
     *
     * @param email to check
     * @return checked e-mail or null if specified e-mail is null
     * @throws IncorrectDataException if given {@link String} is empty or null or not e-mail
     */
    public static String emailCheck(String email) {
        if (isNull(email)) {
            return null;
        }
        String symbol = "[a-zA-Z0-9]";
        String regex = symbol + "(\\w" + "[.]?)+" + symbol + '@' + symbol + "(\\w{2,}" + "[.])+" + symbol + "{2,8}";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            throw new IncorrectDataException("wrong email");
        }
        return email.toLowerCase();
    }

    /**
     * used for mandatory e-mail-fields
     * trims given e-mail. Then checks that given {@link String} is e-mail and sets it to lower case
     *
     * @param email to check and handle
     * @return checked and handled e-mail
     * @throws IncorrectDataException if given {@link String} is empty or null or not e-mail
     */
    public static String emailPrepare(String email) {
        return emailCheck(stringCheck(email));
    }

    /**
     * used for optional phone-fields
     * checks that given {@link String} is phone or null
     *
     * @param phone to check
     * @return checked phone or null if specified phone is null
     * @throws IncorrectDataException if given {@link String} is empty or null or not phone
     */
    public static String phoneCheck(String phone) {
        if (isNull(phone)) {
            return null;
        }
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
     * used for mandatory phone-fields
     * trims given phone. Then checks that given {@link String} is phone
     *
     * @param phone to check and handle
     * @return checked and handled phone
     * @throws IncorrectDataException if given {@link String} is empty or null or not a phone
     */
    public static String phonePrepare(String phone) {
        return phoneCheck(stringCheck(phone));
    }

    /**
     * used for mandatory fields
     *
     * @param object object to check for null
     * @param <E>    object's type
     * @return given object if it's not null
     * @throws IncorrectDataException if given object is null
     */
    public static <E> E checkObject(E object) {
        if (isNull(object)) {
            throw new IncorrectDataException("can't be null");
        }
        return object;
    }
}
