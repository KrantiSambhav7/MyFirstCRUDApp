package com.embark.firstjobapp.review;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> findAll(@PathVariable Long companyId) {
        logger.info("Fetching all reviews for companyId={}", companyId);
        List<Review> reviews = reviewService.findAll(companyId);
        logger.info("Fetched {} reviews for companyId={}", reviews.size(), companyId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@PathVariable Long companyId,@Valid @RequestBody Review review) {
        logger.info("Creating review for companyId={} with title={}", companyId, review.getTitle());
        Boolean success = reviewService.createReview(companyId, review);
        if(success){
            logger.info("Review created successfully for companyId={} with title={}", companyId, review.getTitle());
            return new ResponseEntity<>("Review created successfully", HttpStatus.OK);
        }
        logger.warn("Review creation failed for companyId={} with title={}", companyId, review.getTitle());
        return new ResponseEntity<>("Review creation failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> findReviewById(@PathVariable Long companyId, @PathVariable Long reviewId) {
        logger.info("Fetching reviewId={} for companyId={}", reviewId, companyId);
        Review review = reviewService.findById(companyId, reviewId);
        if(review != null){
            logger.info("Found reviewId={} for companyId={}", reviewId, companyId);
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        logger.warn("ReviewId={} not found for companyId={}", reviewId, companyId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId, @PathVariable Long reviewId, @Valid @RequestBody Review review) {
        logger.info("Updating reviewId={} for companyId={}", reviewId, companyId);
        Boolean success = reviewService.updateReview(companyId, reviewId, review);
        if(success){
            logger.info("ReviewId={} updated successfully for companyId={}", reviewId, companyId);
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        }
        logger.warn("Review update failed for reviewId={} and companyId={}", reviewId, companyId);
        return new ResponseEntity<>("Review update failed", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        logger.info("Deleting reviewId={} for companyId={}", reviewId, companyId);
        Boolean success = reviewService.deleteReview(companyId, reviewId);
        if(success){
            logger.info("ReviewId={} deleted successfully for companyId={}", reviewId, companyId);
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        }
        logger.warn("Review deletion failed for reviewId={} and companyId={}", reviewId, companyId);
        return new ResponseEntity<>("Review deletion failed", HttpStatus.BAD_REQUEST);
    }
}
