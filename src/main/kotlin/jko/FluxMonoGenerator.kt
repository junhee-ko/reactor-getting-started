package jko

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
}

