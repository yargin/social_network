package com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class AccountInfoMvcModel implements Serializable {
    private Account account;
    private String password;
    private String confirmPassword;
    private List<PhoneView> privatePhones;
    private List<PhoneView> workPhones;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<PhoneView> getPrivatePhones() {
        return privatePhones;
    }

    public void setPrivatePhones(List<PhoneView> privatePhones) {
        this.privatePhones = privatePhones;
    }

    public List<PhoneView> getWorkPhones() {
        return workPhones;
    }

    public void setWorkPhones(List<PhoneView> workPhones) {
        this.workPhones = workPhones;
    }
}
