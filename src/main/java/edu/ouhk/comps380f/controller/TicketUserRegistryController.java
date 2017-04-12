package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.TicketUserRepository;
import edu.ouhk.comps380f.model.TicketUser;
import java.io.IOException;
import javax.annotation.Resource;
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
public class TicketUserRegistryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    TicketUserRepository ticketUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        @RequestMapping(value = "registry", method = RequestMethod.GET)
    public ModelAndView registry() {
        return new ModelAndView("registry", "ticketUser", new Form());
    }
    
    @RequestMapping(value = "registry", method = RequestMethod.POST)
    public View registry(Form form) throws IOException {
        TicketUser user = new TicketUser(form.getUsername(),
                passwordEncoder.encode(form.getPassword()),
                new String[]{"ROLE_USER"}
        );
        ticketUserRepo.save(user);
        logger.info("User " + form.getUsername() + " created.");
        return new RedirectView("/login", true);
    }

}
