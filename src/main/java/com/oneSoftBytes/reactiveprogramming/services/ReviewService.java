package com.oneSoftBytes.reactiveprogramming.services;

import com.oneSoftBytes.reactiveprogramming.domain.Review;
import lombok.var;
import reactor.core.publisher.Flux;

import java.util.List;

public class ReviewService {

    public Flux<Review> getReviews(long bookId) {
        var reviewList = List.of(
                new Review(1, bookId, 9.1, "Good Book"),
                new Review(2, bookId, 8.6, "Worth Reading")
        );

        return Flux.fromIterable(reviewList);
    }


}
