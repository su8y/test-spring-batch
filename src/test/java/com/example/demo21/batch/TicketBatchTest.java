package com.example.demo21.batch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles(value = "test")
public class TicketBatchTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "exampleJob")
    private Job job;

    @Test
    // 기본 값 : true
    @Rollback()
    void context_binding_test() {
        String name = "qotndk";
        HashMap<String, JobParameter<?>> map = new HashMap<>();
        JobParameter<?> workerName = new JobParameter<>(name.toUpperCase(), String.class);
        map.put("name", workerName);

        JobParameters jobParameters = new JobParameters(map);
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        }

    }

}
