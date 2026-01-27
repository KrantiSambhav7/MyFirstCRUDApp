package com.embark.firstjobapp.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.codec.ResourceDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {
    
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job created successfully" ,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findUserById(@PathVariable Long id){
        Job job = jobService.findById(id);
        if (job != null) {
            return new ResponseEntity<>(job, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        jobService.deleteById(id);
        return new ResponseEntity<>("Job deleted successfully" ,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> putUserById(@PathVariable Long id,@RequestBody Job updatedJob){
        boolean update = jobService.updateById(id, updatedJob);
        if(update){
            return new ResponseEntity<>("Job updated successfully" ,HttpStatus.OK);
        }
        return new ResponseEntity<>("Update failed" ,HttpStatus.BAD_REQUEST);
    }
}
