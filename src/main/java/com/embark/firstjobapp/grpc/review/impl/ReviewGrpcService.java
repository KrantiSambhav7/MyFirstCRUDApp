package com.embark.firstjobapp.grpc.review.impl;

import java.util.List;

import com.embark.firstjobapp.grpc.review.*;
import com.embark.firstjobapp.review.Review;
import com.embark.firstjobapp.review.ReviewService;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ReviewGrpcService extends ReviewServiceGrpc.ReviewServiceImplBase {

    private final ReviewService reviewService;

    public ReviewGrpcService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public void getReviewById(
            ReviewIdRequest request,
            StreamObserver<ReviewResponse> responseObserver) {

        try {
            Review review = reviewService.findById(
                request.getCompanyId(),
                request.getReviewId()
        );
        

            ReviewResponse response = ReviewResponse.newBuilder()
                    .setId(review.getId())
                    .setRating((int)review.getRating())
                    .setTitle(review.getTitle())
                    .setDescription(review.getDescription())
                    .setCompanyId(review.getCompany().getId())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException()
            );
        }
    }

    @Override
    public void getReviewsByCompanyId(
            CompanyIdRequest request,
            StreamObserver<ReviewListResponse> responseObserver) {

        List<Review> reviews =
        reviewService.getReviewsByCompanyId(
                Long.valueOf(request.getCompanyId())
        );

        ReviewListResponse.Builder responseBuilder =
                ReviewListResponse.newBuilder();

        for (Review review : reviews) {
            responseBuilder.addReviews(
                    ReviewResponse.newBuilder()
                            .setId(review.getId())
                            .setRating((int)review.getRating())
                            .setTitle(review.getTitle())
                            .setDescription(review.getDescription())
                            .setCompanyId(review.getCompany().getId())
                            .build()
            );
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
