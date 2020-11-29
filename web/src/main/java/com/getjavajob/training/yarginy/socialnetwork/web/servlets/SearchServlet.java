package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.service.DataSetsService;
import com.getjavajob.training.yarginy.socialnetwork.service.DataSetsServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;

public class SearchServlet extends HttpServlet {
    private final DataSetsService dataSetsService = new DataSetsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchString = req.getParameter("searchString");
        String page = req.getParameter("page");
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            redirectToReferer(req, resp);
            return;
        }
        SearchableDto found = dataSetsService.searchAccountsGroups(searchString, pageNumber);
        req.setAttribute("found", found.getSearchAbles());
        int allPagesNumber = found.getPages();
        int[] allPages = new int[allPagesNumber];
        for (int i = 0; i < allPagesNumber; i++) {
            allPages[i] = i + 1;
        }
        req.setAttribute("allPages", allPages);
        req.setAttribute("page", pageNumber);
        req.setAttribute("searchString", searchString);
        req.getRequestDispatcher(Jsps.GLOBAL_SEARCH).forward(req, resp);
    }
}
