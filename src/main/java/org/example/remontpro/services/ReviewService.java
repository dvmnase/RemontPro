package org.example.remontpro.services;

import lombok.RequiredArgsConstructor;
import org.example.remontpro.entities.Client;
import org.example.remontpro.entities.Order;
import org.example.remontpro.entities.ReviewEntity;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.models.OrderStatus;
import org.example.remontpro.repositories.ClientRepository;
import org.example.remontpro.repositories.OrderRepository;
import org.example.remontpro.repositories.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository, ClientRepository clientRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    public ReviewEntity leaveReview(Long clientId, Long orderId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Оценка должна быть от 1 до 5.");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Клиент не найден."));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден."));

        if (!order.getClientId().equals(clientId)) {
            throw new IllegalArgumentException("Этот заказ не принадлежит указанному клиенту.");
        }

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Отзыв можно оставить только на завершённый заказ.");
        }

        boolean exists = reviewRepository.existsByOrderId(orderId);
        if (exists) {
            throw new IllegalArgumentException("Отзыв на этот заказ уже был оставлен.");
        }

        ReviewEntity review = new ReviewEntity();
        review.setClientId(clientId);
        review.setOrderId(orderId);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }



    public List<ReviewEntity> getReviewsByService(Long serviceId) {
        return reviewRepository.findAll().stream()
                .filter(r -> {
                    Order order = orderRepository.findById(r.getOrderId()).orElse(null);
                    return order != null && order.getServiceId().equals(serviceId);
                })
                .toList();
    }

}
