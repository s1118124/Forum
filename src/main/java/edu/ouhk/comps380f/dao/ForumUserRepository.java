package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rick Cheung
 */
public interface ForumUserRepository extends JpaRepository<ForumUser, String> {
}
