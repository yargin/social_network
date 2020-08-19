package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class DataChecker {
    private DataChecker() {
    }

    /**
     * used for optionally fields
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
     * used for mandatory fields
     * trims leading-tailing spaces. Then checks that given {@link String} is not null or empty
     *
     * @param string to check
     * @return given {@link String}
     * @throws IncorrectDataException if given {@link String} is empty or null
     */
    public static String stringPrepare(String string) {
        stringTrim(string);
        if (isNull(string) || string.isEmpty()) {
            throw new IncorrectDataException("can't be null or empty");
        }
        return string;
    }

    /**
     * used for optionally e-mail-fields
     * checks that given {@link String} is e-mail or null
     *
     * @param email to check
     * @return checked e-mail or null if specified e-mail was null
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
        return emailCheck(stringPrepare(email));
    }

    /**
     * used for optionally e-mail-fields
     * checks that given {@link String} is e-mail or null
     *
     * @param phone to check
     * @return checked e-mail or null if specified e-mail was null
     * @throws IncorrectDataException if given {@link String} is empty or null or not e-mail
     */
    public static String phoneCheck(String phone) {
        if (isNull(phone)) {
            return null;
        }
        String symbol = "[a-zA-Z0-9]";
        String regex = symbol + "(\\w" + "[.]?)+" + symbol + '@' + symbol + "(\\w{2,}" + "[.])+" + symbol + "{2,8}";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(phone).matches()) {
            throw new IncorrectDataException("wrong email");
        }
        return phone.toLowerCase();
    }
}
