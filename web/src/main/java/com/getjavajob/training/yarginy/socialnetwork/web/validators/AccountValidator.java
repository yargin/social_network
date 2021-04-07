package com.getjavajob.training.yarginy.socialnetwork.web.validators;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static java.time.LocalDate.now;
import static java.util.Objects.isNull;

@Component
public class AccountValidator implements Validator {
    public static final int MIN_AGE = 10;
    public static final int MAX_AGE = 140;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Account.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        checkNullString(account::getName, account::setName, errors, "account.name");
        checkNullString(account::getSurname, account::setSurname, errors, "account.surname");
        checkNullString(account::getEmail, account::setEmail, errors, "account.email");
        String additionalEmailPath = "account.additionalEmail";
        checkNullString(account::getAdditionalEmail, account::setAdditionalEmail, errors, additionalEmailPath);
        checkNullString(account::getCountry, account::setCountry, errors, "account.country");
        checkNullString(account::getCity, account::setCity, errors, "account.city");
        String email = account.getEmail();
        checkEmail(email, "account.email", errors);
        String additionalEmail = account.getAdditionalEmail();
        if (!isNull(additionalEmail)) {
            checkEmail(additionalEmail, additionalEmailPath, errors);
            if (!isNull(email) && email.equals(additionalEmail)) {
                errors.rejectValue(additionalEmailPath, "error.sameAdditionalEmail");
            }
        }
        checkBirthDate(account.getBirthDate(), errors);
    }

    private void checkNullString(Supplier<String> getter, Consumer<String> setter, Errors errors, String field) {
        String value = getter.get();
        if (isNull(value)) {
            errors.rejectValue(field, "error.wrongString");
        } else {
            setter.accept(value);
        }
    }

    private void checkEmail(String email, String param, Errors errors) {
        String symbol = "[a-zA-Z0-9]";
        String regex = symbol + "(\\w" + "[.]?)+" + symbol + '@' + symbol + "(\\w{2,}" + "[.])+" + symbol + "{2,8}";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            errors.rejectValue(param, "error.notEmail");
        }
    }

    private void checkBirthDate(Date date, Errors errors) {
        if (!isNull(date)) {
            LocalDate localDate = date.toLocalDate();
            LocalDate minDate = now().minusYears(MIN_AGE);
            LocalDate maxDate = now().minusYears(MAX_AGE);
            if (localDate.isAfter(minDate)) {
                errors.rejectValue("account.birthDate", "error.tooYoung");
            } else if (localDate.isBefore(maxDate)) {
                errors.rejectValue("account.birthDate", "error.tooOld");
            }
        }
    }
}
