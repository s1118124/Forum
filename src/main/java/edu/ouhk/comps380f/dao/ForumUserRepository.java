package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.ForumUser;
import java.util.List;

/**
 *
 * @author Rick Cheung
 */
public interface ForumUserRepository {
 public void create(ForumUser user);
 public List<ForumUser> findAll();
 public ForumUser findByUsername(String username);
 public void deleteByUsername(String username);
}
