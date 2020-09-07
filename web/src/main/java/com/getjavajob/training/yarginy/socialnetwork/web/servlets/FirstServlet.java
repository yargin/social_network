package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

public class FirstServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try {
            Writer writer = resp.getWriter();
            AccountService accountService = new AccountServiceImpl();
            Map<Account, Collection<Phone>> accountsPhones = accountService.getAllWithPhones();

            for (Map.Entry<Account, Collection<Phone>> entry : accountsPhones.entrySet()) {
                writer.write(entry.getKey().getName() + " " + entry.getKey().getSurname());
                writer.write("<br>");
                for (Phone phone : entry.getValue()) {
                    writer.write(phone.getNumber());
                    writer.write("<br>");
                }
                writer.write("-----------------------------------------");
                writer.write("<br>");
            }
        } catch (IOException ignore) {
        }
//        ServletContext servletContext = getServletContext();
//        servletContext.setAttribute();
    }
}
