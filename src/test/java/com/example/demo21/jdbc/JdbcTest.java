package com.example.demo21.jdbc;

import com.example.demo21.MemberChild;
import com.example.demo21.PushMember;
import com.example.demo21.jpa.TestRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DataJdbcTest
@ActiveProfiles("test")
@Transactional
//@TestPropertySource(locations = "classpath:application-test.yml")
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    void init() {
    }

    @Test
    void DB연결_테스트() throws SQLException {
        dataSource.getConnection();
    }

    @Test
    void PUSHMEMBERCOUNT() {
        Long i = jdbcTemplate.queryForObject("SELECT * FROM PUSH_MEMBER;", Long.class);
        Assertions.assertThat(i).isEqualTo(1L);
    }

    @Test
    void PushMember_값_넣기_테스트() {
//        jdbcTemplate.update("INSERT INTO PUSH_MEMBER(name,success_push) " +
//                        "VALUES('asdfasdf',false);"
//        );
        String insertSql = "INSERT INTO PUSH_MEMBER(name,success_push,id) VALUES(?,?,?);";

        jdbcTemplate.update(insertSql, ps -> {
            ps.setString(1, "qotndk");
            ps.setBoolean(2, false);
            ps.setLong(3, 1);
        });

        String s = "SELECT * FROM PUSH_MEMBER ";
        List<PushMember> query = jdbcTemplate.query(s, memberRowMapper());
        Assertions.assertThat(query.size()).isEqualTo(1);
    }

    private static RowMapper<PushMember> memberRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            boolean successPush = rs.getBoolean("success_push");
            return new PushMember(id, name, successPush);
        };
    }

    @BeforeAll
    void setup() {
        unPushMembers = new ArrayList<>();
        for (int i = 0; i < 10000L; i++) {
            PushMember pushMember = new PushMember((long) i, "member" + i, false);
            pushMember.getMemberChildList().add(new MemberChild(i + " "));
            unPushMembers.add(pushMember);
        }

    }


    @Test
    void 여러_값_넣기_테스트() {
        String insertSql = "INSERT INTO PUSH_MEMBER(name,success_push,id) VALUES(?,?,?);";

        for (PushMember unPushMember : unPushMembers) {
            jdbcTemplate.update(insertSql, ps -> {
                ps.setString(1, unPushMember.getName());
                ps.setBoolean(2, unPushMember.isSuccessPush());
                ps.setLong(3, unPushMember.getId());
            });
        }
    }

    private static List<PushMember> unPushMembers;


    @Test
    void BATCH_여러_값_넣기_테스트() {
        String insertSql = "INSERT INTO PUSH_MEMBER(name,success_push,id) VALUES(?,?,?);";
        jdbcTemplate.batchUpdate(insertSql, unPushMembers, 5000, (ps, argument) -> {
            ps.setLong(3, argument.getId());
            ps.setString(1, argument.getName());
            ps.setBoolean(2, false);
        });

//        jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                PushMember pushMember = unPushMembers.get(i);
//                ps.setString(1, pushMember.getName());
//                ps.setBoolean(2, false);
//                ps.setLong(3, pushMember.getId());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return 100;
//            }
//        });

        Integer j = jdbcTemplate.queryForObject("select count(id) from PUSH_MEMBER", Integer.class);
        System.out.println(j);
    }


}
