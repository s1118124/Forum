package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.exception.AttachmentNotFound;
import edu.ouhk.comps380f.exception.TicketNotFound;
import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Ticket;
import edu.ouhk.comps380f.service.AttachmentService;
import edu.ouhk.comps380f.service.TicketService;
import edu.ouhk.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("post")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = {"", "list"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String list(ModelMap model) {
        model.addAttribute("ticketDatabase", ticketService.getTickets());
        return "list";
    }

    @RequestMapping(value = "view/{ticketId}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("ticketId") long ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        if (ticket == null) {
            return new ModelAndView(new RedirectView("/ticket/list", true));
        }
        return new ModelAndView("view", "ticket", ticket);
    }

    @RequestMapping(value = "create", method = RequestMethod.GET, params = {"type"})
    
    public ModelAndView create(@RequestParam("type") String type) {
        Form form = new Form();
        form.setType(type);
        form.setPostType("post");
        form.setBelongTo(0);
        return new ModelAndView("add", "ticketForm", form);
    }

    public static class Form {

        private String subject;
        private String body;
        private List<MultipartFile> attachments;
        private String type;
        private String postType;
        private long belongTo;

        public String getPostType() {
            return postType;
        }

        public void setPostType(String postType) {
            this.postType = postType;
        }

        public long getBelongTo() {
            return belongTo;
        }

        public void setBelongTo(long belongTo) {
            this.belongTo = belongTo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form, Principal principal,@RequestParam("type") String type) throws IOException {
        long ticketId = ticketService.createTicket(principal.getName(),
                form.getSubject(), form.getBody(), form.getAttachments(), type , form.getPostType(), form.getBelongTo());
        return new RedirectView("/post/view/" + ticketId, true);
    }

    @RequestMapping(
            value = "/{ticketId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("ticketId") long ticketId,
            @PathVariable("attachment") String name) {

        Attachment attachment = attachmentService.getAttachment(ticketId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/ticket/list", true);
    }

    @RequestMapping(
            value = "/{ticketId}/delete/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View deleteAttachment(@PathVariable("ticketId") long ticketId,
            @PathVariable("attachment") String name) throws AttachmentNotFound {
        ticketService.deleteAttachment(ticketId, name);
        return new RedirectView("/ticket/edit/" + ticketId, true);
    }

    @RequestMapping(value = "edit/{ticketId}", method = RequestMethod.GET)
    public ModelAndView showEdit(@PathVariable("ticketId") long ticketId,
            Principal principal, HttpServletRequest request) {
        Ticket ticket = ticketService.getTicket(ticketId);
        if (ticket == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(ticket.getCustomerName()))) {
            return new ModelAndView(new RedirectView("/post/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("ticket", ticket);

        Form ticketForm = new Form();
        ticketForm.setSubject(ticket.getSubject());
        ticketForm.setBody(ticket.getBody());
        modelAndView.addObject("ticketForm", ticketForm);

        return modelAndView;
    }

    @RequestMapping(value = "edit/{ticketId}", method = RequestMethod.POST)
    public View edit(@PathVariable("ticketId") long ticketId, Form form,
            Principal principal, HttpServletRequest request)
            throws IOException, TicketNotFound {
        Ticket ticket = ticketService.getTicket(ticketId);
        if (ticket == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(ticket.getCustomerName()))) {
            return new RedirectView("/post/list", true);
        }

        ticketService.updateTicket(ticketId, form.getSubject(),
                form.getBody(), form.getAttachments());
        return new RedirectView("/post/view/" + ticketId, true);
    }

    @RequestMapping(value = "delete/{ticketId}", method = RequestMethod.GET)
    public View deleteTicket(@PathVariable("ticketId") long ticketId) throws TicketNotFound {
        ticketService.delete(ticketId);
        return new RedirectView("/post/list", true);
    }
    
    
    @RequestMapping(value = "reply", method = RequestMethod.GET, params = {"pid"})
    
    public ModelAndView reply(@RequestParam("pid") long pid) {
        Form form = new Form();
        form.setType("reply");
        form.setPostType("reply");
        form.setBelongTo(pid);
        return new ModelAndView("add", "ticketForm", form);
    }
    
        @RequestMapping(value = "reply", method = RequestMethod.POST)
    public View reply(Form form, Principal principal,@RequestParam("pid") long pid) throws IOException {
        long ticketId = ticketService.createTicket(principal.getName(),
                form.getSubject(), form.getBody(), form.getAttachments(), "reply" ,"reply", pid);
        return new RedirectView("/post/view/" + ticketId, true);
    }
    
    
    

}
