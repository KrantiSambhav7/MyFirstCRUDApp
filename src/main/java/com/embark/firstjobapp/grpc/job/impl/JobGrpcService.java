package com.embark.firstjobapp.grpc.job.impl;

import com.embark.firstjobapp.job.Job;
import com.embark.firstjobapp.job.JobService;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import com.embark.firstjobapp.grpc.job.JobServiceGrpc;
import com.embark.firstjobapp.grpc.job.JobIdRequest;
import com.embark.firstjobapp.grpc.job.JobResponse;

@GrpcService
public class JobGrpcService extends JobServiceGrpc.JobServiceImplBase {

    private final JobService jobService;

    public JobGrpcService(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void getJobById(JobIdRequest request,
                           StreamObserver<JobResponse> responseObserver) {

        Job job = jobService.findById(request.getId());

        JobResponse response = JobResponse.newBuilder()
                .setId(job.getId())
                .setTitle(job.getTitle())
                .setDescription(job.getDescription())
                .setLocation(job.getLocation())
                .setMinSalary(job.getMinSalary())
                .setMaxSalary(job.getMaxSalary())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
