package com.getjavajob.training.yarginy.socialnetwork.web;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class FirstServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        Writer writer = resp.getWriter();

        AccountService accountService = new AccountServiceImpl();
        Map<Account, Collection<Phone>> accountsPhones = accountService.getAllWithPhones();
        Set<Account> accounts = accountsPhones.keySet();
        for (Account account : accounts) {
            writer.write(account.getName() + " " + account.getSurname());
            writer.write("<br>");
            for (Phone phone : accountsPhones.get(account)) {
                writer.write(phone.getNumber());
                writer.write("<br>");
            }
            writer.write("-----------------------------------------");
            writer.write("<br>");
        }
    }
}
