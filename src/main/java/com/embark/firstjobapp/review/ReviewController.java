package com.embark.firstjobapp.review;

import java.util.List;

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
@RequestMapping("/companies/{companyId}")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> findAll(@PathVariable Long companyId) {
        return ResponseEntity.ok(reviewService.findAll(companyId));
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@PathVariable Long companyId, @RequestBody Review review) {
        Boolean success = reviewService.createReview(companyId, review);
        if(success){
            return new ResponseEntity<>("Review created successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review creation failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> findReviewById(@PathVariable Long companyId, @PathVariable Long reviewId) {
        Review review = reviewService.findById(companyId, reviewId);
        if(review != null){
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody Review review) {
        Boolean success = reviewService.updateReview(companyId, reviewId, review);
        if(success){
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review update failed", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        Boolean success = reviewService.deleteReview(companyId, reviewId);
        if(success){
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review deletion failed", HttpStatus.BAD_REQUEST);
    }
}
