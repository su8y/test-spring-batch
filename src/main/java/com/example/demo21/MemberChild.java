package com.example.demo21;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "MEMBER_CHILD")
@Data
public class MemberChild {

    @Id
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private PushMember pushMember;

    public MemberChild(String name) {
        this.name = name;
    }
    public MemberChild() {

    }
}
