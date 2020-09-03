package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public final class DataCheckWithLength {
    public static final int DEFAULT_LENGTH = 200;

    private DataCheckWithLength() {
    }

    /**
     * used for optional {@link String} fields
     * trims given {@link String} and checks its not too long
     *
     * @param string    to check and trim
     * @param maxLength max length applicable
     * @return checked and trimmed {@link String}, null if was null or empty or only-spaces
     * @throws IncorrectDataException if given {@link String} is too long
     */
    public static String stringOptional(String string, int maxLength) {
        if (!isNull(string)) {
            String stringOptional = string.trim();
            if (stringOptional.length() > maxLength) {
                throw new IncorrectDataException("too long");
            }
            if (!stringOptional.isEmpty()) {
                return stringOptional;
            }
        }
        return null;
    }

    /**
     * used for mandatory {@link String} fields
     * trims given {@link String} and checks its not too long. Then checks that its not empty, only-spaces or null
     *
     * @param string    to check
     * @param maxLength max length applicable
     * @return given {@link String}
     * @throws IncorrectDataException if given {@link String} is empty, null, only-spaces or too long
     */
    public static String stringMandatory(String string, int maxLength) {
        String stringMandatory = stringOptional(string, maxLength);
        if (isNull(stringMandatory)) {
            throw new IncorrectDataException("can't be null or empty");
        }
        return stringMandatory;
    }

    /**
     * used for optional e-mail-fields
     * trims given {@link String} and checks its not too long. Then checks that given {@link String} is e-mail and sets
     * it to lower case
     *
     * @param email     to check
     * @param maxLength max length applicable
     * @return checked e-mail or null if specified e-mail is null
     * @throws IncorrectDataException if given {@link String} is not an e-mail
     */
    public static String emailOptional(String email, int maxLength) {
        String emailOptional = stringOptional(email, maxLength);
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
     * @param email     to check
     * @param maxLength max length applicable
     * @return checked e-mail
     * @throws IncorrectDataException if given {@link String} is empty, null, too long or not an e-mail
     *                                is empty, null, only-spaces or too long
     */
    public static String emailMandatory(String email, int maxLength) {
        String emailMandatory = stringMandatory(email, maxLength);
        return isEmail(emailMandatory).toLowerCase();
    }

    /**
     * used for optional phone-fields
     * trims given {@link String} and checks its not too long. Then checks that given {@link String} is phone or null
     *
     * @param phone     to check
     * @param maxLength max length applicable
     * @return checked phone or null if specified phone is null
     * @throws IncorrectDataException if given {@link String} is empty or null or not phone
     */
    public static String phoneOptional(String phone, int maxLength) {
        String phoneOptional = stringOptional(phone, maxLength);
        if (isNull(phoneOptional)) {
            return null;
        }
        return isPhone(phoneOptional);
    }

    /**
     * used for mandatory phone-fields
     * trims given {@link String} and checks its not too long. Then checks that given {@link String} is phone
     *
     * @param phone     to check and handle
     * @param maxLength max length applicable
     * @return checked and handled phone
     * @throws IncorrectDataException if given {@link String} is empty or null or not a phone
     */
    public static String phoneMandatory(String phone, int maxLength) {
        String phoneMandatory = stringMandatory(phone, maxLength);
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
}
