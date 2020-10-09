package com.getjavajob.training.yarginy.socialnetwork.web.servlets.old;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImplOld;

import javax.servlet.ServletException;
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
            AccountService accountService = new AccountServiceImplOld();
            Map<Account, Collection<Phone>> accountsPhones = accountService.getAllWithPhones();

//            for (Map.Entry<Account, Collection<Phone>> entry : accountsPhones.entrySet()) {
//                writer.write(entry.getKey().getName() + " " + entry.getKey().getSurname());
//                writer.write("<br>");
//                for (Phone phone : entry.getValue()) {
//                    writer.write(phone.getNumber());
//                    writer.write("<br>");
//                }
//                writer.write("-----------------------------------------");
//                writer.write("<br>");
//            }

            req.setAttribute("members", accountsPhones.entrySet());
            req.getRequestDispatcher("/WEB-INF/jsps/old/memberlist.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
