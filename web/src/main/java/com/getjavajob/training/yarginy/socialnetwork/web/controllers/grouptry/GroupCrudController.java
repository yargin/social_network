package com.getjavajob.training.yarginy.socialnetwork.web.controllers.grouptry;

import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping("/group")
public class GroupCrudController {
//    private final GroupService groupService;
//
//    @Autowired
//    public GroupCrudController(GroupService groupService) {
//        this.groupService = groupService;
//    }
//
//    @GetMapping("/update")
//    public ModelAndView showGroupUpdate(HttpServletRequest req, HttpServletResponse resp, HttpSession session,
//                                        @RequestAttribute("id") long requestedId) {
//        ModelAndView modelAndView = new ModelAndView(GROUP_UPDATE_VIEW);
//
//        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp);
//
//        //select at first visit
//        Group group = updater.getOrCreateGroup(() -> groupService.get(requestedId));
//
//        //save to session if wasn't
//        if (isNull(session.getAttribute(GROUP))) {
//            session.setAttribute(GROUP, group);
//        }
//        updater.initGroupAttributes(group);
//
//        return modelAndView;
//    }
//
//    @PostMapping("/update")
//    public ModelAndView doUpdateGroup(HttpServletRequest req, HttpServletResponse resp, @ModelAttribute Group group,
//                                      @SessionAttribute("group") Group storedGroup,
//                                      @RequestAttribute("id") long requestedId, @RequestParam MultipartFile photo) {
//        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp);
//        if ("cancel".equals(req.getParameter("save"))) {
//            return new ModelAndView(updater.NEWacceptActionOrRetry(true, null));
//        }
//
//        group.setId(requestedId);
//        group.setPhoto(storedGroup.getPhoto());
//        try {
//            byte[] photoBytes = photo.getBytes();
//            if (photoBytes.length > 0) {
//                group.setPhoto(photo.getBytes());
//            }
//        } catch (IOException ignore) {}
//
//
//
//
//        //for further views
//
//        updater.getValuesFromParams(group);
//
//        boolean accepted = updater.isParamsAccepted();
//        if (!accepted) {
//            safeDoGet(req, resp);
//        } else {
//            updateGroup(updater, group, storedGroup);
//        }
//    }
//
//    private void updateGroup(UpdateGroupFieldsHelper updater, Group group, Group storedGroup) throws ServletException,
//            IOException {
//        boolean updated;
//        try {
//            updated = groupService.updateGroup(group, storedGroup);
//        } catch (IncorrectDataException e) {
//            updater.handleInfoExceptions(e, this::safeDoGet);
//            return;
//        }
//        updater.setSuccessUrl(Pages.GROUP_WALL, Attributes.GROUP_ID, "" + group.getId());
//        updater.acceptActionOrRetry(updated, this::safeDoGet);
//    }
}
