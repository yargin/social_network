package com.getjavajob.training.yarginy.socialnetwork.web.validators;

import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.AccountInfoMvcModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountInfoValidator implements Validator {
    private final AccountValidator accountValidator;
    private final PhonesListValidator phonesListValidator;

    @Autowired
    public AccountInfoValidator(AccountValidator accountValidator, PhonesListValidator phonesListValidator) {
        this.accountValidator = accountValidator;
        this.phonesListValidator = phonesListValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountInfoMvcModel.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountInfoMvcModel model = (AccountInfoMvcModel) target;
        accountValidator.validate(model.getAccount(), errors);
        phonesListValidator.validate(model.getPrivatePhones(), errors);
        phonesListValidator.validate(model.getWorkPhones(), errors);
    }
}
