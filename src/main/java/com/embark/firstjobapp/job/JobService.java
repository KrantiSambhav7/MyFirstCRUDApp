package com.embark.firstjobapp.job;
import java.util.List;
import com.embark.firstjobapp.dto.JobRequest;

public interface JobService {
    List<Job> findAll();
    void createJob(JobRequest jobRequest);
    Job findById(Long id);
    void deleteById(Long id);
    boolean updateById(Long id, Job updatedJob);
}
