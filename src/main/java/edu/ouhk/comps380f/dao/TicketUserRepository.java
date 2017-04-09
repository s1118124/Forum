package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.TicketUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketUserRepository extends JpaRepository<TicketUser, String> {
}
