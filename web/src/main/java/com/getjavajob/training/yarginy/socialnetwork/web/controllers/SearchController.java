package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.service.DataSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.SEARCH_VIEW;

@Controller
public class SearchController {
    private static final int LIMIT = 10;
    private final DataSetsService dataSetsService;

    @Autowired
    public SearchController(DataSetsService dataSetsService) {
        this.dataSetsService = dataSetsService;
    }

    @GetMapping("/search")
    public ModelAndView getSearchResults(@RequestParam(required = false) String searchString, HttpServletRequest req,
                                         @RequestParam int page) {
        ModelAndView modelAndView = new ModelAndView(SEARCH_VIEW);
        SearchableDto found = dataSetsService.searchAccountsGroups(searchString, page, LIMIT);
        modelAndView.addObject("found", found.getSearchAbles());
        int allPagesNumber = found.getPages();
        int[] allPages = new int[allPagesNumber];
        for (int i = 0; i < allPagesNumber; i++) {
            allPages[i] = i + 1;
        }
        modelAndView.addObject("allPages", allPages);
        modelAndView.addObject("page", page);
        modelAndView.addObject("searchString", searchString);
        return modelAndView;
    }
}
