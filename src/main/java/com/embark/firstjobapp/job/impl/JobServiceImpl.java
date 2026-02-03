package com.embark.firstjobapp.job.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.embark.firstjobapp.company.Company;
import com.embark.firstjobapp.company.CompanyRepository;
import com.embark.firstjobapp.dto.JobRequest;
import com.embark.firstjobapp.exception.ResourceNotFoundException;
import com.embark.firstjobapp.job.Job;
import com.embark.firstjobapp.job.JobRepository;
import com.embark.firstjobapp.job.JobService;


import jakarta.transaction.Transactional;


@Service
public class JobServiceImpl implements JobService{
    // private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    

    public JobServiceImpl(JobRepository jobRepository, 
    CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Transactional
    @Override
    public void createJob(JobRequest jobRequest) {
    
        Company company = companyRepository.findById(jobRequest.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + jobRequest.getCompanyId()));
    
        Job job = new Job();
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setMinSalary(jobRequest.getMinSalary());
        job.setMaxSalary(jobRequest.getMaxSalary());
        job.setLocation(jobRequest.getLocation());
        job.setCompany(company);
    
        jobRepository.save(job);
    }
    

    @Override
public Job findById(Long id) {
    return jobRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
}


@Override
public void deleteById(Long id) {
    if (!jobRepository.existsById(id)) {
        throw new ResourceNotFoundException("Job not found with id: " + id);
    }
    jobRepository.deleteById(id);
}


    @Transactional
    @Override
    public boolean updateById(Long id, Job updatedJob) {
        Job job = jobRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

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
