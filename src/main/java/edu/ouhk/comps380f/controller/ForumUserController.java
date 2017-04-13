package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.ForumUserRepository;
import edu.ouhk.comps380f.model.ForumUser;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("user")
public class ForumUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    ForumUserRepository forumUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("forumUsers", forumUserRepo.findAll());
        return "listUser";
    }

    public static class Form {

        private String username;
        private String password;
        private String[] roles;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("addUser", "forumUser", new Form());
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form) throws IOException {
        ForumUser user = new ForumUser();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        for (String role : form.getRoles()) {
            user.addRole(role);
        }
        forumUserRepo.create(user);
        logger.info("User " + form.getUsername() + " created.");
        return new RedirectView("/user/list", true);
    }

    @RequestMapping(value = "delete/{username}", method = RequestMethod.GET)
    public View deleteUser(@PathVariable("username") String username) {
        forumUserRepo.deleteByUsername(username);
        logger.info("User " + username + " deleted.");
        return new RedirectView("/user/list", true);
    }
    
    @RequestMapping(value = "registry", method = RequestMethod.GET)
    public ModelAndView registry() {
        return new ModelAndView("registry", "forumUser", new Form());
    }
    
    @RequestMapping(value = "registry", method = RequestMethod.POST)
    public View registry(Form form) throws IOException {
        ForumUser user = new ForumUser();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        for (String role : form.getRoles()) {
            user.addRole(role);
        }
        forumUserRepo.create(user);
        logger.info("User " + form.getUsername() + " created.");
        return new RedirectView("/index", true);
    }

}
