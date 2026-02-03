package com.embark.firstjobapp.grpc.company.impl;

import java.io.ObjectInputFilter.Status;

import com.embark.firstjobapp.company.Company;
import com.embark.firstjobapp.company.CompanyService;
import com.embark.firstjobapp.grpc.company.CompanyServiceGrpc;
import com.embark.firstjobapp.grpc.company.CompanyIdRequest;
import com.embark.firstjobapp.grpc.company.CompanyResponse;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CompanyGrpcService extends CompanyServiceGrpc.CompanyServiceImplBase {

    private final CompanyService companyService;

    public CompanyGrpcService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void getCompanyById(CompanyIdRequest request,
                               StreamObserver<CompanyResponse> responseObserver) {

                                Company company = companyService.findById(
                                    Long.valueOf(request.getId())
                            );
                            if (company == null) {
                                return;
                            }
                            


                            CompanyResponse response = CompanyResponse.newBuilder()
                            .setId(company.getId().intValue())
                            .setName(company.getName())
                            .setDescription(company.getDescription())
                            .build();
                    

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
