package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
