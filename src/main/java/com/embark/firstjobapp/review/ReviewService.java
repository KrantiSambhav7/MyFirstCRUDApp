package com.embark.firstjobapp.review;

import java.util.List;

import org.jspecify.annotations.Nullable;

public interface ReviewService {
    List<Review> findAll(Long companyId);
    Boolean createReview(Long companyId, Review review);
    Review findById(Long companyId, Long reviewId);
    Boolean updateReview(Long companyId, Long reviewId, Review review);
    Boolean deleteReview(Long companyId, Long reviewId);  
}