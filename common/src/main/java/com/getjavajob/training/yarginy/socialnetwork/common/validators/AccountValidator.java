package com.getjavajob.training.yarginy.socialnetwork.common.validators;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import org.springframework.validation.Errors;

import static java.util.Objects.isNull;

public class AccountValidator extends AbstractValidator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Group.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        if (isNull(stringOptional(account.getName()))) {
            errors.reject("errname", "error.wrongString");
        }

    }
}
