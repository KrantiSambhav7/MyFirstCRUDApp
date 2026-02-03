package com.embark.firstjobapp.job.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.embark.firstjobapp.company.Company;
import com.embark.firstjobapp.company.CompanyRepository;
import com.embark.firstjobapp.dto.JobRequest;
import com.embark.firstjobapp.exception.ResourceNotFoundException;
import com.embark.firstjobapp.job.Job;
import com.embark.firstjobapp.job.JobRepository;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private JobServiceImpl jobService;

    @Test
    void shouldReturnAllJobs() {
        when(jobRepository.findAll()).thenReturn(List.of(new Job(), new Job()));

        List<Job> jobs = jobService.findAll();

        assertEquals(2, jobs.size());
        verify(jobRepository).findAll();
    }

    @Test
    void shouldCreateJobWhenCompanyExists() {
        JobRequest request = new JobRequest();
        request.setTitle("Backend Dev");
        request.setDescription("Spring Boot");
        request.setMinSalary(5);
        request.setMaxSalary(10);
        request.setLocation("Remote");
        request.setCompanyId(1L);

        Company company = new Company();

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        jobService.createJob(request);

        verify(jobRepository).save(any(Job.class));
    }

    @Test
    void shouldThrowExceptionWhenCreatingJobWithInvalidCompany() {
        JobRequest request = new JobRequest();
        request.setCompanyId(99L);

        when(companyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> jobService.createJob(request));

        verify(jobRepository, never()).save(any());
    }

    @Test
    void shouldReturnJobWhenFoundById() {
        Job job = new Job();
        job.setId(1L);

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Job result = jobService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenJobNotFoundById() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> jobService.findById(1L));
    }

    @Test
    void shouldDeleteJobWhenExists() {
        when(jobRepository.existsById(1L)).thenReturn(true);

        jobService.deleteById(1L);

        verify(jobRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingJob() {
        when(jobRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> jobService.deleteById(1L));

        verify(jobRepository, never()).deleteById(any());
    }

    @Test
    void shouldUpdateJobSuccessfully() {
        Job existingJob = new Job();
        existingJob.setId(1L);

        Job updatedJob = new Job();
        updatedJob.setTitle("Updated");
        updatedJob.setDescription("Updated desc");
        updatedJob.setMinSalary(10);
        updatedJob.setMaxSalary(20);
        updatedJob.setLocation("Hybrid");

        when(jobRepository.findById(1L)).thenReturn(Optional.of(existingJob));

        boolean result = jobService.updateById(1L, updatedJob);

        assertTrue(result);
        verify(jobRepository).save(existingJob);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingJob() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> jobService.updateById(1L, new Job()));
    }
}
