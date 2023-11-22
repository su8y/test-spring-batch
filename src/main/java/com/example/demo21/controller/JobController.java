package com.example.demo21.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class JobController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    @Qualifier(value = "exampleJob")
    private Job job;

    @GetMapping("/job/step1")
    public String jobStep1(
            @RequestParam("name") final String name
    ) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException,
            JobRestartException {
        HashMap<String, JobParameter<?>> map = new HashMap<>();
        JobParameter<?> workerName = new JobParameter<>(name.toUpperCase(), String.class);
        map.put("name", workerName);

        JobParameters jobParameters = new JobParameters(map);

        jobLauncher.run(job, jobParameters);
        return "ok";
    }

    @GetMapping("/jobs/{jobName}")
    public List<JobInstance> catalogJob(
            @PathVariable(value = "jobName") String jobName,
            @RequestParam(value = "param",required = false) String param
    ) {
        List<JobInstance> jobInstances = jobExplorer.getJobInstances(jobName, 0, 10);
        return jobInstances;
    }

}
