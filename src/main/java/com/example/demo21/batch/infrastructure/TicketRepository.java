package com.example.demo21.batch.infrastructure;

import com.example.demo21.batch.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,String> {
}
