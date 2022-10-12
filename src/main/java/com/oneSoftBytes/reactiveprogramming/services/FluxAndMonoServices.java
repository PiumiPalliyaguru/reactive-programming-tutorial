package com.oneSoftBytes.reactiveprogramming.services;



import lombok.var;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoServices {

    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana")).log();
    }

    public Flux<String> fruitsFluxMap() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> fruitsFluxFilter(int number) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .filter(s -> s.length() > number)
                .log();
    }

    public Flux<String> fruitsFluxFilterMap(int number) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .filter(s -> s.length() > number)
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> fruitsFluxFlatMap() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .log();
    }

    public Flux<String> fruitsFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split(""))
                        .delayElements(Duration.ofMillis(
                        new Random().nextInt(1000)
                )))
                .log();
    }

    public Flux<String> fruitsFluxConcatMap() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .concatMap(s -> Flux.just(s.split(""))
                        .delayElements(Duration.ofMillis(
                                new Random().nextInt(1000)
                        )))
                .log();
    }
    public Mono<String> fruitsMono() {
        return Mono.just("Apple").log();
    }

    public Mono<List<String>> fruitsMonoFlatMap() {
        return Mono.just("Apple")
                .flatMap(s -> Mono.just(List.of(s.split(""))))
                .log();
    }

    public Flux<String> fruitsMonoFlatMapMany() {
        return Mono.just("Apple")
                .flatMapMany(s -> Flux.just(s.split("")))
                .log();
    }

    public Flux<String> fruitsFluxTransform(int number) {
        Function<Flux<String>, Flux<String>> filterData =
                data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .transform(filterData) // .filter(s -> s.length() > number)
                .log();
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number) {
        Function<Flux<String>, Flux<String>> filterData =
                data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .transform(filterData)
                .defaultIfEmpty("Default")
                .log();
    }

    public Flux<String> fruitsFluxTransformSwitchIfEmpty(int number) {
        Function<Flux<String>, Flux<String>> filterData =
                data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .transform(filterData)
                .switchIfEmpty(Flux.just("Pineapple", "Jack Fruit")
                        .transform(filterData))
                .log();
    }

    public Flux<String> fruitsFluxConcat() {
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");

        return Flux.concat(fruits, veggies)
                .log();
    }

    public Flux<String> fruitsFluxConcatWith() {
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");

        return fruits.concatWith(veggies);
    }

    public Flux<String> fruitsMonoConcatWith() {
        var fruits = Mono.just("Mango");
        var veggies = Mono.just("Tomato");

        return fruits.concatWith(veggies);
    }

    public Flux<String> fruitsFluxMerge() {
        var fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        var veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));

        return Flux.merge(fruits, veggies);
    }

    public Flux<String> fruitsFluxMergeWith() {
        var fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        var veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));

        return fruits.mergeWith(veggies);
    }

    public Flux<String> fruitsFluxMergeSequential() {
        var fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        var veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));

        return Flux.mergeSequential(fruits, veggies);
    }

    public Flux<String> fruitsFluxZip() {
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");

        return Flux.zip(fruits, veggies,
                (first, second) -> first+second).log();
    }

    public Flux<String> fruitsFluxZipWith() {
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");

        return fruits.zipWith(veggies,
                (first, second) -> first+second).log();
    }

    public Flux<String> fruitsFluxZipTuple() {
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");
        var moreVeggies = Flux.just("Potato", "Beans");

        return Flux.zip(fruits, veggies, moreVeggies)
                .map(objects -> objects.getT1() + objects.getT2() + objects.getT3());
    }

    //ZipWith operator with Mono
    public Mono<String> fruitsMonoZipWith() {
        var fruits = Mono.just("Mango");
        var veggies = Mono.just("Tomato");

        return fruits.zipWith(veggies,
                (first, second) -> first+second).log();
    }

    //DoOn callbacks
    public Flux<String> fruitsFluxFilterDoOn(int number) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .filter(s -> s.length() > number)
                .doOnNext(s -> {
                    System.out.println("s = " + s);
                })
                .doOnSubscribe(subscription -> {
                    System.out.println("subscription.toString() = " + subscription.toString());
                })
                .doOnComplete(() -> System.out.println("Completed"));
    }

    //Error handling
    public Flux<String> fruitsFluxOnErrorReturn() {
        return Flux.just("Apple", "Mango")
                .concatWith(Flux.error(
                        new RuntimeException("Exception Occurred")
                ))
                .onErrorReturn("Orange");
    }

    public Flux<String> fruitsFluxOnErrorContinue() {
        return Flux.just("Apple", "Mango", "Orange")
                .map(s -> {
                    if(s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception occurred");
                    return s.toUpperCase();
                })
                .onErrorContinue((e, f) -> {
                    System.out.println("e = " + e);
                    System.out.println("f = " + f);
                });
    }

    public Flux<String> fruitsFluxOnErrorMap() {
        return Flux.just("Apple", "Mango", "Orange")
                .checkpoint("Error check point 1")
                .map(s -> {
                    if(s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception occurred");
                    return s.toUpperCase();
                })
                .checkpoint("Error check point 2")
                .onErrorMap(throwable -> {
                    System.out.println("throwable = " + throwable);
                    return  new IllegalStateException("From onError Map");
                });
    }

    public Flux<String> fruitsFluxDoOnError() {
        return Flux.just("Apple", "Mango", "Orange")
                .map(s -> {
                    if(s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception occurred");
                    return s.toUpperCase();
                })
                .doOnError(throwable -> {
                    System.out.println("throwable = " + throwable);
                });
    }

    public static void main(String[] args) {

        FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();

        fluxAndMonoServices.fruitsFlux()
                .subscribe(s -> {
                    System.out.println("s = " + s);
                });

        fluxAndMonoServices.fruitsMono()
                .subscribe(s -> {
                    System.out.println("Mono = " + s);
                });

    }
}
