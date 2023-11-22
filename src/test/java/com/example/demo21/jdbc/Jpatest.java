package com.example.demo21.jdbc;

import com.example.demo21.PushMember;
import com.example.demo21.jpa.TestRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.annotations.BatchSize;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class Jpatest {

    @Autowired
    private EntityManager entityManager;

    private List<PushMember> unPushMembers;

    @BeforeAll
    void setup() {
        unPushMembers = new ArrayList<>();
        for (int i = 0; i < 100L; i++) {
            PushMember pushMember = new PushMember((long) i, "member" + i, false);
//            pushMember.getMemberChildList().add(new MemberChild("child" + i));
            unPushMembers.add(pushMember);
        }

    }

    @Test
    @Transactional
    void JPA_FIND_테스트() {
        unPushMembers.forEach(e -> {
            entityManager.persist(e);
        });
        entityManager.flush();

//        List<PushMember> all = testRepository.findAll();
//        System.out.println(all.get(0).getMemberChildList().get(0).getName());
//        System.out.println(all.get(1).getMemberChildList().get(0).getName());
//        System.out.println(all.get(2).getMemberChildList().get(0).getName());

    }

    @Autowired
    TestRepository testRepository;

    @Test
    void JPA_FIND_테스트REPO() {
        testRepository.saveAll(unPushMembers);
//        unPushMembers.forEach(e->{
//            testRepository.save(e);
//        });
    }

}
