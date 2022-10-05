package com.oneSoftBytes.reactiveprogramming.services;

import com.oneSoftBytes.reactiveprogramming.domain.BookInfo;
import lombok.var;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BookInfoService {

    public Flux<BookInfo> getBooks() {
        var books = List.of(
                new BookInfo(1, "Book one", "Author one", "111111"),
                new BookInfo(2, "Book two", "Author two", "222222"),
                new BookInfo(3, "Book three", "Author three", "333333")
        );

        return Flux.fromIterable(books);
    }

    public Mono<BookInfo> getBookById(long bookId) {
        var book = new BookInfo(bookId, "Book one", "Author one", "111111");

        return Mono.just(book);
    }
}
