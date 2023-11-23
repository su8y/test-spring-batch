package com.example.demo21.batch.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    private String title;
}
