package com.oneSoftBytes.reactiveprogramming.services;

import com.oneSoftBytes.reactiveprogramming.domain.Book;
import com.oneSoftBytes.reactiveprogramming.domain.Review;
import com.oneSoftBytes.reactiveprogramming.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
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
                })
                .onErrorMap(throwable -> {
                    log.error("Exception is : " + throwable);
                    return new BookException("Exception occurred while fetching Books");
                })
                .log();
    }

    public Mono<Book> getBookById(long bookId) {
        var book = bookInfoService.getBookById(bookId);
        var review = reviewService.getReviews(bookId).collectList();

        return book
                .zipWith(review, (b, r) -> new Book(b, r));

    }

    public Flux<Book> getBooksRetry() {
        var allBooks = bookInfoService.getBooks();

        return allBooks
                .flatMap(bookInfo -> {
                    //wrapping this to Mono because I need list of review as one object
                    Mono<List<Review>> reviews =
                            reviewService.getReviews(bookInfo.getBookId()).collectList();

                    // then I get the reviews object and bookInfo object to create new Book object
                    return reviews
                            .map(review -> new Book(bookInfo, review));
                })
                .onErrorMap(throwable -> {
                    log.error("Exception is : " + throwable);
                    return new BookException("Exception occurred while fetching Books");
                })
                .retry(3)
                .log();
    }

    public Flux<Book> getBooksRetryWhen() {
      //  var retrySpec = getRetryBackoffSpec();
        var allBooks = bookInfoService.getBooks();

        return allBooks
                .flatMap(bookInfo -> {
                    //wrapping this to Mono because I need list of review as one object
                    Mono<List<Review>> reviews =
                            reviewService.getReviews(bookInfo.getBookId()).collectList();

                    // then I get the reviews object and bookInfo object to create new Book object
                    return reviews
                            .map(review -> new Book(bookInfo, review));
                })
                .onErrorMap(throwable -> {
                    log.error("Exception is : " + throwable);
                    return new BookException("Exception occurred while fetching Books");
                })
                .retryWhen(getRetryBackoffSpec())
                .log();
    }

    private static RetryBackoffSpec getRetryBackoffSpec() {
        return Retry.backoff(
                        3,
                        Duration.ofMillis(1000)
                ).filter(throwable -> throwable instanceof BookException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        Exceptions.propagate(retrySignal.failure()));
    }

}
