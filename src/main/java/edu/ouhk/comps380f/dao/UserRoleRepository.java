package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
