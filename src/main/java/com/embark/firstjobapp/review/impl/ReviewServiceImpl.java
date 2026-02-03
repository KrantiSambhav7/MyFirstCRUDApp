package com.embark.firstjobapp.review.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.embark.firstjobapp.company.Company;
import com.embark.firstjobapp.company.CompanyService;
import com.embark.firstjobapp.review.Review;
import com.embark.firstjobapp.review.ReviewRepository;
import com.embark.firstjobapp.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> findAll(Long companyId) {
        List<Review> list = reviewRepository.findByCompanyId(companyId);
        return list;
    }

    @Override
    public Boolean createReview(Long companyId, Review review) {
        Company company = companyService.findById(companyId);
        if(company != null){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review findById(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);
    }

    @Override
    public Boolean updateReview(Long companyId, Long reviewId, Review review) {
        Review existingReview = findById(companyId, reviewId);
        if(existingReview != null){
            existingReview.setTitle(review.getTitle());
            existingReview.setDescription(review.getDescription());
            existingReview.setRating(review.getRating());
            reviewRepository.save(existingReview);
            return true;
        }
        return false;
    }

    public List<Review> getReviewsByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }
    

    @Override
    public Boolean deleteReview(Long companyId, Long reviewId) {
       if(companyService.findById(companyId) != null && reviewRepository.findById(reviewId).isPresent()){
        Review review = reviewRepository.findById(reviewId).get();
        Company company = review.getCompany();
        company.getReviews().remove(review);
        companyService.updateCompany(companyId, company);   
        reviewRepository.deleteById(reviewId);
        return true;
       }
       return false;
    }
}
