package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.service.AttachmentService;
import edu.ouhk.comps380f.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/lecture"}, method = RequestMethod.GET)
    public String lecture(ModelMap model) {
        model.addAttribute("ticketDatabase", ticketService.getTickets());
        return "lectureList";
    }

    @RequestMapping(value = {"/lab"}, method = RequestMethod.GET)
    public String lab() {
        return "labList";
    }

    @RequestMapping(value = {"/other"}, method = RequestMethod.GET)
    public String other() {
        return "otherList";
    }

}
