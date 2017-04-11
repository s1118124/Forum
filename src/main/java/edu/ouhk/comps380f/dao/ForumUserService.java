package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.ForumUser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author B
 */

@Service
public class ForumUserService implements UserDetailsService {

    @Autowired
    ForumUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         {
            ForumUser user = userRepo.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User '" + username + "' not found.");
            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }

}
