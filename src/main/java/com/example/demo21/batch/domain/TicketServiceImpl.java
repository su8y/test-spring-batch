package com.example.demo21.batch.domain;

import com.example.demo21.batch.infrastructure.TicketRepository;
import com.example.demo21.batch.infrastructure.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Transactional
    @Override
    public void insertTicket(JsonFoo pushMember) {
        Ticket ticket = new Ticket();
        ticket.setTitle(pushMember.toString());
        ticketRepository.save(ticket);
    }
}
