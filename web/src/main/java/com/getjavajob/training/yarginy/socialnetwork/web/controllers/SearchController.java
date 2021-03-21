package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.service.DataSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getSearchResults(@RequestParam(required = false) String searchString) {
        ModelAndView modelAndView = new ModelAndView(SEARCH_VIEW);
        modelAndView.addObject("searchString", searchString == null ? "" : searchString);
        return modelAndView;
    }

    @GetMapping("/find")
    @ResponseBody
    public SearchableDto getResult(@RequestParam(required = false) String string, @RequestParam int page) {
        return dataSetsService.searchAccountsGroups(string, page, LIMIT);
    }
}
