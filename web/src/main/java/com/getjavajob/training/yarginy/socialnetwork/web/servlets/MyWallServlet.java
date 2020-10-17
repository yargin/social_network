package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhotoImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import com.getjavajob.training.yarginy.socialnetwork.web.attributes.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

public class MyWallServlet extends HttpServlet {
    private static final String JSP = "/WEB-INF/jsps/myWall.jsp";
    private final AccountDao accountDao = new AccountDaoImpl();
    private final PhoneDao phoneDao = new PhoneDaoImpl();
    private final AccountPhotoDao accountPhotoDao = new AccountPhotoDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo service.get messages
        HttpSession session = req.getSession();
        long id = (long) session.getAttribute(SessionAttributes.USER_ID);
        Account account = accountDao.select(id);
        req.setAttribute("user", account);

        Collection<Phone> phones = phoneDao.selectPhonesByOwner(account);
        Collection<Phone> privatePhones = phones.stream().filter(phone -> phone.getType() == PhoneType.PRIVATE).
                collect(Collectors.toList());
        req.setAttribute("privatePhones", privatePhones);
        Collection<Phone> workPhones = phones.stream().filter(phone -> phone.getType() == PhoneType.WORK).
                collect(Collectors.toList());
        req.setAttribute("workPhones", workPhones);

        AccountPhoto accountPhoto =  accountPhotoDao.select(account);
        String base64Image = Base64.getEncoder().encodeToString(accountPhoto.getPhoto());
        req.setAttribute("photo", base64Image);

        req.getRequestDispatcher(JSP).forward(req, resp);
    }
}
