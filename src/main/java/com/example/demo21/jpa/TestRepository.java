package com.example.demo21.jpa;

import com.example.demo21.PushMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<PushMember, Long> {
}
