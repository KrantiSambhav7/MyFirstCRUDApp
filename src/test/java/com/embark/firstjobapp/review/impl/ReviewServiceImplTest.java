package com.embark.firstjobapp.review.impl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.embark.firstjobapp.company.Company;
import com.embark.firstjobapp.company.CompanyService;
import com.embark.firstjobapp.review.Review;
import com.embark.firstjobapp.review.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void shouldReturnAllReviewsForCompany() {
        Long companyId = 1L;
        List<Review> reviews = List.of(new Review(), new Review());

        when(reviewRepository.findByCompanyId(companyId)).thenReturn(reviews);

        List<Review> result = reviewService.findAll(companyId);

        assertEquals(2, result.size());
        verify(reviewRepository).findByCompanyId(companyId);
    }

    @Test
    void shouldCreateReviewWhenCompanyExists() {
        Long companyId = 1L;
        Review review = new Review();
        Company company = new Company();

        when(companyService.findById(companyId)).thenReturn(company);

        Boolean result = reviewService.createReview(companyId, review);

        assertTrue(result);
        assertEquals(company, review.getCompany());
        verify(reviewRepository).save(review);
    }

    @Test
    void shouldNotCreateReviewWhenCompanyDoesNotExist() {
        Long companyId = 99L;
        Review review = new Review();

        when(companyService.findById(companyId)).thenReturn(null);

        Boolean result = reviewService.createReview(companyId, review);

        assertFalse(result);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void shouldReturnReviewWhenFound() {
        Long companyId = 1L;
        Long reviewId = 10L;

        Review review = new Review();
        review.setId(reviewId);

        when(reviewRepository.findByCompanyId(companyId))
                .thenReturn(List.of(review));

        Review result = reviewService.findById(companyId, reviewId);

        assertNotNull(result);
        assertEquals(reviewId, result.getId());
    }

    @Test
    void shouldReturnNullWhenReviewNotFound() {
        when(reviewRepository.findByCompanyId(1L))
                .thenReturn(List.of());

        Review result = reviewService.findById(1L, 10L);

        assertNull(result);
    }

    @Test
    void shouldUpdateReviewSuccessfully() {
        Long companyId = 1L;
        Long reviewId = 1L;

        Review existing = new Review();
        existing.setId(reviewId);

        Review updated = new Review();
        updated.setTitle("Updated");
        updated.setDescription("Updated desc");
        updated.setRating(4);

        when(reviewRepository.findByCompanyId(companyId))
                .thenReturn(List.of(existing));

        Boolean result = reviewService.updateReview(companyId, reviewId, updated);

        assertTrue(result);
        verify(reviewRepository).save(existing);
    }

    @Test
    void shouldFailToUpdateWhenReviewNotFound() {
        when(reviewRepository.findByCompanyId(1L))
                .thenReturn(List.of());

        Boolean result = reviewService.updateReview(1L, 10L, new Review());

        assertFalse(result);
    }

    @Test
    void shouldDeleteReviewSuccessfully() {
        Long companyId = 1L;
        Long reviewId = 1L;

        Review review = new Review();
        Company company = new Company();
        company.setReviews(new ArrayList<>(List.of(review)));
        review.setCompany(company);

        when(companyService.findById(companyId)).thenReturn(company);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        Boolean result = reviewService.deleteReview(companyId, reviewId);

        assertTrue(result);
        verify(reviewRepository).deleteById(reviewId);
    }
}
