package com.getjavajob.training.yarginy.socialnetwork.web.validators;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

@Component
public class GroupValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Group.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Group group = (Group) target;

        String name = group.getName();
        if (isNull(name) || name.trim().isEmpty()) {
            errors.rejectValue("name", "error.wrongString", "NOT APPLICABLE");
        } else {
            group.setName(name.trim());
        }
    }
}
