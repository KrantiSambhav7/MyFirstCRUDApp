package com.embark.firstjobapp.job;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.embark.firstjobapp.dto.JobRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll() {
        logger.info("GET /jobs called to fetch all jobs");
        List<Job> jobs = jobService.findAll();
        logger.info("Fetched {} jobs", jobs.size());
        return ResponseEntity.ok(jobs);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@Valid @RequestBody JobRequest jobRequest) {
        logger.info("POST /jobs called with title='{}', companyId={}", jobRequest.getTitle(), jobRequest.getCompanyId());
        try {
            jobService.createJob(jobRequest);
            logger.info("Job '{}' created successfully for companyId={}", jobRequest.getTitle(), jobRequest.getCompanyId());
            return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Job creation failed: {}", e.getMessage(), e);
            throw e; // Let GlobalExceptionHandler handle this
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findUserById(@PathVariable Long id) {
        logger.info("GET /jobs/{} called", id);
        try {
            Job job = jobService.findById(id);
            logger.info("Job with id={} fetched successfully", id);
            return new ResponseEntity<>(job, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to fetch job with id={}: {}", id, e.getMessage());
            throw e; // Let GlobalExceptionHandler handle this
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        logger.info("DELETE /jobs/{} called", id);
        try {
            jobService.deleteById(id);
            logger.info("Job with id={} deleted successfully", id);
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to delete job with id={}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putUserById(@PathVariable Long id, @RequestBody Job updatedJob) {
        logger.info("PUT /jobs/{} called to update job with title='{}'", id, updatedJob.getTitle());
        try {
            boolean update = jobService.updateById(id, updatedJob);
            if (update) {
                logger.info("Job with id={} updated successfully", id);
                return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
            } else {
                logger.warn("Job update failed for id={}", id);
                return new ResponseEntity<>("Update failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Failed to update job with id={}: {}", id, e.getMessage());
            throw e;
        }
    }
}
