package com.getjavajob.training.yarginy.socialnetwork.web.validators.composite;

import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.AccountInfoMvcModel;
import com.getjavajob.training.yarginy.socialnetwork.web.validators.PasswordValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {
    private final AccountInfoValidator accountInfoValidator;
    private final PasswordValidator passwordValidator;

    public RegistrationValidator(AccountInfoValidator accountInfoValidator, PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
        this.accountInfoValidator = accountInfoValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountInfoMvcModel.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountInfoMvcModel model = (AccountInfoMvcModel) target;
        accountInfoValidator.validate(model, errors);
        passwordValidator.validate(model, errors);
    }
}
