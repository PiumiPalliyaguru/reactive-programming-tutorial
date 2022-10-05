package com.oneSoftBytes.reactiveprogramming.services;

import com.oneSoftBytes.reactiveprogramming.domain.Book;
import com.oneSoftBytes.reactiveprogramming.domain.Review;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class BookService {

    private BookInfoService bookInfoService;
    private ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks() {
        var allBooks = bookInfoService.getBooks();

        return allBooks
                .flatMap(bookInfo -> {
                    //wrapping this to Mono because I need list of review as one object
                    Mono<List<Review>> reviews =
                            reviewService.getReviews(bookInfo.getBookId()).collectList();

                    // then I get the reviews object and bookInfo object to create new Book object
                    return reviews
                            .map(review -> new Book(bookInfo, review));
                }).log();
    }

}
