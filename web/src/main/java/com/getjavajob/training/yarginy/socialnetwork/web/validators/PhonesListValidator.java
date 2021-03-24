package com.getjavajob.training.yarginy.socialnetwork.web.validators;

import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.PhoneView;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Collection;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Component
public class PhonesListValidator {
    public void validate(Collection<PhoneView> phones, Errors errors) {
        if (!isNull(phones)) {
            for (PhoneView phoneView : phones) {
                checkPhone(phoneView, errors);
            }
        }
    }

    private void checkPhone(PhoneView phoneView, Errors errors) {
        String phone = phoneView.getNumber();
        String space = "[\\s]?";
        String regex = "[+]?" + space + "\\d{1,4}" + "([\\s-]?\\d+){0,6}";
        String regexWithBraces = "[+]?" + space + "\\d{1,4}" + space + "[(]" + space + "\\d+" + space + "[)]" + space +
                "([\\s-]?\\d+){0,6}";
        Pattern pattern = Pattern.compile(regex);
        Pattern patternWithBraces = Pattern.compile(regexWithBraces);
        if (!pattern.matcher(phone).matches() && !patternWithBraces.matcher(phone).matches()) {
            errors.reject("error.notPhone");
            phoneView.setError("error.notPhone");
        }
    }
}
