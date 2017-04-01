/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

/**
 *
 * @author Wong Sze Man
 */
import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Topic;
import edu.ouhk.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("lecture")
public class LectureTopicController {

    private volatile long TOPIC_ID_SEQUENCE = 1;
    private Map<Long, Topic> topicDatabase = new LinkedHashMap<>();

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("topicDatabase", topicDatabase);
        return "lectureTopicList";
    }

    @RequestMapping(value = "view/{topicId}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("topicId") long topicId) {
        Topic topic = this.topicDatabase.get(topicId);
        if (topic == null) {
            return new ModelAndView(new RedirectView("/lecture/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("lectureTopicView");
        modelAndView.addObject("topicId", Long.toString(topicId));
        modelAndView.addObject("topic", topic);
        return modelAndView;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("addToLecture", "topicForm", new Form());
    }

    public static class Form {

        private String title;
        private String textContent;
        private List<MultipartFile> attachments;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form, Principal principal) throws IOException {
        Topic topic = new Topic();
        topic.setId(this.getNextTopicId());
        topic.setUserID(principal.getName());
        topic.setTitle(form.getTitle());
        topic.setTextContent(form.getTextContent());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                topic.addAttachment(attachment);
            }
        }
        this.topicDatabase.put(topic.getId(), topic);
        return new RedirectView("/lecture/view/" + topic.getId(), true);
    }

    private synchronized long getNextTopicId() {
        return this.TOPIC_ID_SEQUENCE++;
    }

    @RequestMapping(
            value = "/{topicId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("topicId") long topicId,
            @PathVariable("attachment") String name) {
        Topic topic = this.topicDatabase.get(topicId);
        if (topic != null) {
            Attachment attachment = topic.getAttachment(name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }
        return new RedirectView("/lecture/list", true);
    }

    @RequestMapping(
            value = "/{topicId}/delete/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View deleteAttachment(@PathVariable("topicId") long topicId,
            @PathVariable("attachment") String name) {
        Topic topic = this.topicDatabase.get(topicId);
        if (topic != null) {
            if (topic.hasAttachment(name)) {
                topic.deleteAttachment(name);
            }
        }
        return new RedirectView("/lecture/edit/" + topicId, true);
    }

    @RequestMapping(value = "edit/{topicId}", method = RequestMethod.GET)
    public ModelAndView showEdit(@PathVariable("topicId") long topicId) {
        Topic topic = this.topicDatabase.get(topicId);
        if (topic == null) {
            return new ModelAndView(new RedirectView("/lecture/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("editLectureTopic");
        modelAndView.addObject("topicId", Long.toString(topicId));
        modelAndView.addObject("topic", topic);

        Form topicForm = new Form();
        topicForm.setTitle(topic.getTitle());
        topicForm.setTextContent(topic.getTextContent());
        modelAndView.addObject("topicForm", topicForm);

        return modelAndView;
    }

    @RequestMapping(value = "edit/{topicId}", method = RequestMethod.POST)
    public View edit(@PathVariable("topicId") long topicId, Form form)
            throws IOException {
        Topic topic = this.topicDatabase.get(topicId);
        topic.setTitle(form.getTitle());
        topic.setTextContent(form.getTextContent());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                topic.addAttachment(attachment);
            }
        }
        this.topicDatabase.put(topic.getId(), topic);
        return new RedirectView("/lecture/view/" + topic.getId(), true);
    }

    @RequestMapping(value = "delete/{topicId}", method = RequestMethod.GET)
    public View deleteTopic(@PathVariable("topicId") long topicId) {
        if (this.topicDatabase.containsKey(topicId)) {
            this.topicDatabase.remove(topicId);
        }
        return new RedirectView("/lecture/list", true);
    }

}
