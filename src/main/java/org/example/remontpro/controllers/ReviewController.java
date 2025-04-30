package org.example.remontpro.controllers;

import lombok.RequiredArgsConstructor;
import org.example.remontpro.entities.ReviewEntity;
import org.example.remontpro.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // POST: Оставить отзыв
    @PostMapping
    public ResponseEntity<ReviewEntity> leaveReview(
            @RequestParam Long clientId,
            @RequestParam Long orderId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment
    ) {
        return ResponseEntity.ok(reviewService.leaveReview(clientId, orderId, rating, comment));
    }

    // GET: Получить отзывы на услугу
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ReviewEntity>> getReviewsByService(@PathVariable Long serviceId) {
        return ResponseEntity.ok(reviewService.getReviewsByService(serviceId));
    }
}
