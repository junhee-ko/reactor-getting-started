package jko

import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*

class FluxMonoGenerator {

    fun namesFlux(): Flux<String> {
        val names = listOf("ko", "jun", "hee")

        return Flux.fromIterable(names)
    }

    fun namesFluxImmutable(): Flux<String> {
        val names = listOf("ko", "jun", "hee")
        val namesFlux = Flux.fromIterable(names)
        namesFlux.map { it.uppercase(Locale.getDefault()) }

        return namesFlux
    }

    fun namesFluxMapAndFilter(strLen: Long): Flux<String> {
        val names = listOf("ko", "jun", "hee")

        return Flux.fromIterable(names)
            .map { it.uppercase(Locale.getDefault()) }
            .filter { it.length > strLen }
    }

    fun namesMono(): Mono<String> {
        return Mono.just("ko")
    }

    fun namesMonoMapAndFilter(strLen: Long): Mono<String> {
        return Mono.just("ko")
            .map { it.uppercase(Locale.getDefault()) }
            .filter { it.length > strLen }
    }

    fun namesFluxFlatMap(strLen: Long): Flux<String> {
        val names = listOf("ko", "jun", "hee")

        return Flux.fromIterable(names)
            .map { it.uppercase(Locale.getDefault()) }
            .filter { it.length > strLen }
            .flatMap { this.splitString(it) }
    }

    fun namesFluxFlatMapAsync(strLen: Long): Flux<String> {
        val names = listOf("ko", "jun", "hee")

        return Flux.fromIterable(names)
            .map { it.uppercase(Locale.getDefault()) }
            .filter { it.length > strLen }
            .flatMap { this.splitStringWithDelay(it) }
    }

    fun namesFluxConcatMap(strLen: Long): Flux<String> {
        val names = listOf("ko", "jun", "hee")

        return Flux.fromIterable(names)
            .map { it.uppercase(Locale.getDefault()) }
            .filter { it.length > strLen }
            .concatMap { this.splitStringWithDelay(it) }
    }

    fun namesMonoFlatMap(strLen: Long): Mono<List<String>> {
        return Mono.just("junhee")
            .map { it.uppercase(Locale.getDefault()) }
            .filter { it.length > strLen }
            .flatMap { this.splitStringMono(it) }
    }

    fun namesMonoFlatMapMany(strLen: Long): Flux<String> {
        return Mono.just("junhee")
            .map { it.uppercase(Locale.getDefault()) }
            .filter { it.length > strLen }
            .flatMapMany { this.splitStringWithDelay(it) }
    }

    fun namesFluxTransform(strLen: Long): Flux<String> {
        val names = listOf("ko", "jun", "hee")

        return Flux.fromIterable(names)
            .transform { name ->
                name
                    .map { it.uppercase(Locale.getDefault()) }
                    .filter { it.length > strLen }
            }
            .flatMap { this.splitString(it) }
    }

    fun namesFluxTransformDefaultIfEmpty(strLen: Long): Flux<String> {
        val names = listOf("ko", "jun", "hee")

        return Flux.fromIterable(names)
            .transform { name ->
                name
                    .map { it.uppercase(Locale.getDefault()) }
                    .filter { it.length > strLen }
            }
            .flatMap { this.splitString(it) }
            .defaultIfEmpty("default")
    }

    fun namesFluxTransformSwitchIfEmpty(strLen: Long): Flux<String> {
//        val filterMap = fun(name: Flux<String>): Publisher<String>? {
//            return name
//                .map { it.uppercase(Locale.getDefault()) }
//                .filter { it.length > strLen }
//        }

        val filterMap = { name: Flux<String> ->
            name
                .map { it.uppercase(Locale.getDefault()) }
                .filter { it.length > strLen }
                .flatMap { this.splitString(it) }
        }

        val names = listOf("ko", "jun", "hee")

        val defaultFlux = Flux.just("default")
            .transform(filterMap)

        return Flux.fromIterable(names)
            .transform(filterMap)
            .switchIfEmpty(defaultFlux)
    }

    // concat() subscribes to the publishers in a sequence ( compared to merge function )
    fun concat(): Flux<String> {
        val abcFlux = Flux.just("a", "b", "c")
        val defFlux = Flux.just("d", "e", "f")

        return Flux.concat(abcFlux, defFlux)
    }

    fun concatWith(): Flux<String> {
        val abcFlux = Flux.just("a", "b", "c")
        val defFlux = Flux.just("d", "e", "f")

        return abcFlux.concatWith(defFlux)
    }

    fun concatMono(): Flux<String> {
        val aMono = Mono.just("a")
        val bMono = Mono.just("b")

        return Flux.concat(aMono, bMono)
    }

    fun concatWithMono(): Flux<String> {
        val aMono = Mono.just("a")
        val bMono = Mono.just("b")

        return aMono.concatWith(bMono)
    }

    // publishers are subscribed eagerly and merge in an interleaved fashion
    fun merge(): Flux<String> {
        val abcFlux = Flux.just("A", "B", "C")
            .delayElements(Duration.ofMillis(100))

        val defFlux = Flux.just("D", "E", "F")
            .delayElements(Duration.ofMillis(125))

        return Flux.merge(abcFlux, defFlux)
    }

    fun mergeWith(): Flux<String> {
        val abcFlux = Flux.just("A", "B", "C")
            .delayElements(Duration.ofMillis(100))

        val defFlux = Flux.just("D", "E", "F")
            .delayElements(Duration.ofMillis(125))

        return abcFlux.mergeWith(defFlux)
    }

    fun mergeWithMono(): Flux<String> {
        val aMono = Mono.just("A")
        val bMono = Mono.just("B")

        return aMono.mergeWith(bMono)
    }

    // the publishers are subscribed eagerly and the merge happens in a sequence
    fun mergeSequential(): Flux<String> {
        val abcFlux = Flux.just("A", "B", "C")
            .delayElements(Duration.ofMillis(100))

        val defFlux = Flux.just("D", "E", "F")
            .delayElements(Duration.ofMillis(125))

        return Flux.mergeSequential(abcFlux, defFlux)
    }

    private fun splitString(name: String): Flux<String> {
        val split: List<String> = name.split("").filter { it.isNotEmpty() }

        return Flux.fromIterable(split)
    }

    private fun splitStringWithDelay(name: String): Flux<String> {
        val delay = Random().nextInt(1000)
        val split: List<String> = name.split("").filter { it.isNotEmpty() }

        return Flux.fromIterable(split)
            .delayElements(Duration.ofMillis(delay.toLong()))
    }

    private fun splitStringMono(name: String): Mono<List<String>> {
        val split: List<String> = name.split("").filter { it.isNotEmpty() }

        return Mono.just(split)
    }
}

