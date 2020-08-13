package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class StringHandler {
    private StringHandler() {
    }

    /**
     * handles given {@link String} - trims leading-tailing spaces
     *
     * @param string to handle
     * @return handled {@link String}
     */
    public static String trimString(String string) {
        if (!isNull(string)) {
            string = string.trim();
        }
        return string;
    }

    /**
     * checks that given {@link String} is not null or empty
     *
     * @param string to check
     * @return given {@link String}
     * @throws IncorrectDataException if given {@link String} is empty or null
     */
    public static String checkString(String string) {
        if (isNull(string) || string.isEmpty()) {
            throw new IncorrectDataException("can't be null or empty");
        }
        return string;
    }

    /**
     * checks that given {@link String} is not null or empty. Then trims leading-tailing spaces
     *
     * @param string to check and handle
     * @return checked and handled {@link String}
     * @throws IncorrectDataException if given {@link String} is empty or null
     */
    public static String checkAndTrim(String string) {
        return trimString(checkString(string));
    }

    /**
     * checks that given {@link String} is e-mail
     *
     * @param email to check
     * @return checked e-mail
     * @throws IncorrectDataException if given {@link String} is empty or null or not e-mail
     */
    public static String checkEmail(String email) {
        checkString(email);
        String symbols = "[a-zA-Z0-9]";
        String regex = "^" + symbols + "(\\w" + "[.]?)+" + symbols + "@" + "[a-zA-Z0-9]" + "(\\w{2,}" + "[.])+" +
                symbols + "{2,8}";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            throw new IncorrectDataException("wrong email");
        }
        return email;
    }

    /**
     * trims given e-mail. Then checks that given {@link String} is e-mail and sets to lower case
     *
     * @param email to check and handle
     * @return checked and handled e-mail
     * @throws IncorrectDataException if given {@link String} is empty or null or not e-mail
     */
    public static String checkAndPrepareEmail(String email) {
        return checkEmail(trimString(email)).toLowerCase();
    }
}
