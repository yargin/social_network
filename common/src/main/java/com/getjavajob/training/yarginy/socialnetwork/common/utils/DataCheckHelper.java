package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public final class DataCheckHelper {
    public static final short MIN_PASSWORD = 6;
    public static final short MAX_PASSWORD = 20;
    public static final short MIN_AGE = 10;
    public static final short MAX_AGE = 140;

    private DataCheckHelper() {
    }

    /**
     * validates optional {@link String} fields
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
     * validates mandatory {@link String} fields
     * trims given {@link String}. Then checks that its not empty, only-spaces or null
     *
     * @param string to check
     * @return given {@link String}
     * @throws IncorrectDataException if given {@link String} is empty, null or only-spaces
     */
    public static String stringMandatory(String string) {
        String stringMandatory = stringOptional(string);
        if (isNull(stringMandatory)) {
            throw new IncorrectDataException(IncorrectData.WRONG_STRING);
        }
        return stringMandatory;
    }

    /**
     * validates optional e-mail-fields
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
     * validates mandatory e-mail-fields
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
     * validates optional phone-fields
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
     * validates mandatory phone-fields
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
     * validates mandatory password-fields
     * trims given {@link String}. Then checks that given {@link String} is phone
     *
     * @param password to check and handle
     * @return checked and handled phone
     * @throws IncorrectDataException if given {@link String} is empty, null or not a password
     */
    public static String passwordMandatory(String password) {
        String passwordMandatory = stringMandatory(password);
        return isPassword(passwordMandatory);
    }

    /**
     * validates mandatory object-fields
     *
     * @param object object to check for null
     * @param <E>    object's type
     * @return given non-null object
     * @throws IncorrectDataException if given object is null
     */
    public static <E> E objectMandatory(E object) {
        if (isNull(object)) {
            throw new IncorrectDataException("can't be null");
        }
        return object;
    }

    /**
     * validates optional email-fields
     * checks that given {@link String} matches email-pattern
     *
     * @param email to check
     * @return checked email
     * @throws IncorrectDataException if given {@link String} doesn't match email-pattern
     */
    private static String isEmail(String email) {
        String symbol = "[a-zA-Z0-9]";
        String regex = symbol + "(\\w" + "[.]?)+" + symbol + '@' + symbol + "(\\w{2,}" + "[.])+" + symbol + "{2,8}";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            throw new IncorrectDataException(IncorrectData.NOT_AN_EMAIL);
        }
        return email;
    }

    /**
     * validates optional phone-fields
     * checks that given {@link String} matches phone-pattern
     *
     * @param phone to check
     * @return checked phone
     * @throws IncorrectDataException if given {@link String} doesn't match phone-pattern
     */
    private static String isPhone(String phone) {
        String space = "[\\s]?";
        String regex = "[+]?" + space + "\\d{1,4}" + "([\\s-]?\\d+)+";
        String regexWithBraces = "[+]?" + space + "\\d{1,4}" + space + "[(]" + space + "\\d+" + space + "[)]" + space +
                "([\\s-]?\\d+)+";
        Pattern pattern = Pattern.compile(regex);
        Pattern patternWithBraces = Pattern.compile(regexWithBraces);
        if (!pattern.matcher(phone).matches() && !patternWithBraces.matcher(phone).matches()) {
            throw new IncorrectDataException(IncorrectData.NOT_A_PHONE);
        }
        return phone;
    }

    /**
     * validates optional password-fields
     * checks that given {@link String} matches password-pattern
     *
     * @param password to check
     * @return checked password
     * @throws IncorrectDataException if given {@link String} too long, too short or doesn't match password-pattern
     */
    private static String isPassword(String password) {
        if (password.length() < MIN_PASSWORD) {
            throw new IncorrectDataException(IncorrectData.PASSWORD_TOO_SHORT);
        }
        if (password.length() > MAX_PASSWORD) {
            throw new IncorrectDataException(IncorrectData.PASSWORD_TOO_LONG);
        }
        Pattern lettersDigitsOnly = Pattern.compile("[a-zA-Z0-9]+");
        if (!lettersDigitsOnly.matcher(password).matches()) {
            throw new IncorrectDataException(IncorrectData.NOT_A_PASSWORD);
        }
        Pattern hasLetterPattern = Pattern.compile(".*[a-zA-Z].*");
        if (!hasLetterPattern.matcher(password).matches()) {
            throw new IncorrectDataException(IncorrectData.NOT_A_PASSWORD);
        }
        Pattern hasDigitPattern = Pattern.compile(".*\\d.*");
        if (!hasDigitPattern.matcher(password).matches()) {
            throw new IncorrectDataException(IncorrectData.NOT_A_PASSWORD);
        }
        return password;
    }

    /**
     * validates specified date relationally current date
     *
     * @param date to check
     * @return checked date
     * @throws IncorrectDataException if given date is too distant or too early from current
     */
    public static Date eligibleAge(Date date) {
        if (!isNull(date)) {
            LocalDate localDate = date.toLocalDate();
            LocalDate minDate = LocalDate.now().minusYears(MIN_AGE);
            LocalDate maxDate = LocalDate.now().minusYears(MAX_AGE);
            if (localDate.isAfter(minDate)) {
                throw new IncorrectDataException(IncorrectData.TOO_YOUNG);
            } else if (localDate.isBefore(maxDate)) {
                throw new IncorrectDataException(IncorrectData.TOO_OLD);
            }
        }
        return date;
    }

    /**
     * checks that id is applicable. 0 means that entity is not saved yet, -1 means that it doesn't exist(nullEntity)
     *
     * @param id to check
     * @return checked id
     */
    public static long checkId(long id) {
        if (id == 0) {
            throw new DataFlowViolationException("must be read from storage");
        } else if (id < -1) {
            throw new IncorrectDataException("wrong id");
        }
        return id;
    }
}
