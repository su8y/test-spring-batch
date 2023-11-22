package com.example.demo21;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name = "PUSH_MEMBER")
public class PushMember {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    private boolean successPush;

    @OneToMany(mappedBy = "pushMember",cascade = CascadeType.PERSIST)
    List<MemberChild> memberChildList = new ArrayList<>();

    public PushMember() {

    }

    public PushMember(Long id, String name, boolean successPush) {
        this.id = id;
        this.name = name;
        this.successPush = successPush;
    }
}
