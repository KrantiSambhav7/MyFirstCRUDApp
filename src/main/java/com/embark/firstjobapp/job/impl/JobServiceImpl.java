package com.embark.firstjobapp.job.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.embark.firstjobapp.job.Job;
import com.embark.firstjobapp.job.JobRepository;
import com.embark.firstjobapp.job.JobService;

import jakarta.transaction.Transactional;


@Service
public class JobServiceImpl implements JobService{
    // private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;
    

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Transactional
    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateById(Long id, Job updatedJob) {
        Job job = jobRepository.findById(id).orElse(null);
        if(job != null){
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}
