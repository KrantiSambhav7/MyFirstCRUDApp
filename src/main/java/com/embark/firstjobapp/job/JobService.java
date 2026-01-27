package com.embark.firstjobapp.job;

import java.util.List;

public interface JobService {
    List<Job> findAll();
    void createJob(Job job);
    Job findById(Long id);
    void deleteById(Long id);
    boolean updateById(Long id, Job updatedJob);
}
