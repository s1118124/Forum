package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.ForumUser;
import edu.ouhk.comps380f.model.UserRole;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
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
 * @author Rick Cheung
 */

@Service
public class ForumUserService implements UserDetailsService {

    @Resource
    ForumUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         {
            ForumUser user = userRepo.findOne(username);
            if (user == null) {
                throw new UsernameNotFoundException("User '" + username + "' not found.");
            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (UserRole role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getRole()));
            }
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }

}
