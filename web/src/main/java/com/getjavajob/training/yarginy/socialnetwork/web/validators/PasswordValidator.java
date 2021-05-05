package com.getjavajob.training.yarginy.socialnetwork.web.validators;

import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.AccountInfoMvcModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Component
public class PasswordValidator implements Validator {
    private static final String NOT_PASSWORD = "error.notPassword";
    private static final int MIN_PASSWORD = 6;
    private static final int MAX_PASSWORD = 20;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountInfoMvcModel.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountInfoMvcModel model = (AccountInfoMvcModel) target;
        String password = model.getPassword();
        if (isNull(password)) {
            errors.rejectValue("password", NOT_PASSWORD);
        } else if (!password.equals(model.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "error.passwordNotMatch");
        } else {
            validateStringPassword(password, errors);
        }
    }

    private void validateStringPassword(String password, Errors errors) {
        String fieldName = "password";
        if (isNull(password)) {
            errors.rejectValue(fieldName, NOT_PASSWORD);
            return;
        }
        if (password.length() < MIN_PASSWORD) {
            errors.rejectValue(fieldName, "error.passwordTooShort");
        }
        if (password.length() > MAX_PASSWORD) {
            errors.rejectValue(fieldName, "error.passwordTooLong");
        }
        Pattern lettersDigitsOnly = Pattern.compile("[a-zA-Z0-9]+");
        Pattern hasLetterPattern = Pattern.compile(".*[a-zA-Z].*");
        Pattern hasDigitPattern = Pattern.compile(".*\\d.*");
        if (!lettersDigitsOnly.matcher(password).matches() || !hasLetterPattern.matcher(password).matches() ||
                !hasDigitPattern.matcher(password).matches()) {
            errors.rejectValue(fieldName, NOT_PASSWORD);
        }
    }
}
