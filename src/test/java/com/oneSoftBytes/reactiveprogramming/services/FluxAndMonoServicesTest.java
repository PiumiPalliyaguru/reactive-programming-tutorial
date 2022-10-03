package com.oneSoftBytes.reactiveprogramming.services;

import lombok.var;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoServicesTest {

    FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();

    @Test
    void fruitsFlux() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFlux();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Orange", "Banana")
                .verifyComplete();
    }

    @Test
    void fruitsMono() {
        var fruitsMono = fluxAndMonoServices.fruitsMono();

        StepVerifier.create(fruitsMono)
                .expectNext("Apple")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMap() {
        var fruitsFlux = fluxAndMonoServices.fruitsFluxMap();
        
        StepVerifier.create(fruitsFlux)
                .expectNext("MANGO", "ORANGE", "BANANA")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilter() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxFilter(5);

        StepVerifier.create(fruitsFlux)
                .expectNext("Orange", "Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilterMap() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxFilterMap(5);

        StepVerifier.create(fruitsFlux)
                .expectNext("ORANGE", "BANANA")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMap() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxFlatMap();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMapAsync() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxFlatMapAsync();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsMonoFlatMap() {
        var fruitsMono = fluxAndMonoServices.fruitsMonoFlatMap();

        StepVerifier.create(fruitsMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatMap() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxConcatMap();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsMonoFlatMapMany() {

        var fruitsMono = fluxAndMonoServices.fruitsMonoFlatMapMany();

        StepVerifier.create(fruitsMono)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransform() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxTransform(5);

        StepVerifier.create(fruitsFlux)
                .expectNext("Orange", "Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformDefaultIfEmpty() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxTransformDefaultIfEmpty(10);

        StepVerifier.create(fruitsFlux)
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformSwitchIfEmpty() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxTransformSwitchIfEmpty(8);

        StepVerifier.create(fruitsFlux)
                .expectNext("Pineapple", "Jack Fruit")
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcat() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxConcat();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Orange", "Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatWith() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxConcatWith().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Orange", "Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsMonoConcatWith() {
        var  fruitsFlux = fluxAndMonoServices.fruitsMonoConcatWith().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Tomato")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMerge() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxMerge().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Tomato","Orange", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMergeWith() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxMergeWith().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Tomato","Orange", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMergeSequential() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxMergeSequential().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Orange", "Tomato",  "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxZip() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxZip();

        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomato", "OrangeLemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxZipWith() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxZipWith();

        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomato", "OrangeLemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxZipTuple() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxZipTuple().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomatoPotato", "OrangeLemonBeans")
                .verifyComplete();
    }

    @Test
    void fruitsMonoZipWith() {
        var  fruitsFlux = fluxAndMonoServices.fruitsMonoZipWith();

        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomato")
                .verifyComplete();
    }


    @Test
    void fruitsFluxFilterDoOn() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxFilterDoOn(5).log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Orange", "Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorReturn() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxOnErrorReturn().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Apple", "Mango", "Orange")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorContinue() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxOnErrorContinue().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("APPLE", "ORANGE")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorMap() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxOnErrorMap().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("APPLE")
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void fruitsFluxDoOnError() {
        var  fruitsFlux = fluxAndMonoServices.fruitsFluxDoOnError().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("APPLE")
                .expectError(RuntimeException.class)
                .verify();
    }
}