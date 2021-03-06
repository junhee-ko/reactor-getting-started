package jko

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class FluxMonoGeneratorTest {

    private val fluxMonoGenerator = FluxMonoGenerator()

    @Test
    fun namesFlux() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFlux()

        // then
        StepVerifier.create(names)
            .expectNext("ko", "jun", "hee")
            .verifyComplete()
    }

    @Test
    fun namesFluxImmutable() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFluxImmutable()

        // then
        StepVerifier.create(names)
            .expectNext("ko", "jun", "hee")
            .verifyComplete()
    }

    @Test
    fun namesFluxMapAndFilter() {
        // given
        val strLen = 2L

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFluxMapAndFilter(strLen)

        // then
        StepVerifier.create(names)
            .expectNext("JUN", "HEE")
            .verifyComplete()
    }

    @Test
    fun namesMono() {
        // given

        // when
        val namesMono: Mono<String> = fluxMonoGenerator.namesMono()

        // then
        StepVerifier.create(namesMono)
            .expectNext("ko")
            .verifyComplete()
    }

    @Test
    fun namesMonoMapAndFilter() {
        // given
        val strLen = 2L

        // when
        val namesMono: Mono<String> = fluxMonoGenerator.namesMonoMapAndFilter(strLen)

        // then
        StepVerifier.create(namesMono)
            .verifyComplete()
    }

    @Test
    fun namesFluxFlatMap() {
        // given
        val strLen = 2L

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFluxFlatMap(strLen)

        // then
        StepVerifier.create(names)
            .expectNext("J", "U", "N", "H", "E", "E")
            .verifyComplete()
    }

    @Test
    fun namesFluxFlatMapAsync() {
        // given
        val strLen = 2L

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFluxFlatMapAsync(strLen).log()

        // then
        StepVerifier.create(names)
//            .expectNext("J", "U", "N", "H", "E", "E")
            .expectNextCount(6)
            .verifyComplete()
    }

    @Test
    fun namesFluxConcatMap() {
        // given
        val strLen = 2L

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFluxConcatMap(strLen).log()

        // then
        StepVerifier.create(names)
            .expectNext("J", "U", "N", "H", "E", "E")
//            .expectNextCount(6)
            .verifyComplete()
    }

    @Test
    fun namesMonoFlatMap() {
        // given
        val strLen = 2L

        // when
        val names: Mono<List<String>> = fluxMonoGenerator.namesMonoFlatMap(strLen).log()

        // then
        StepVerifier.create(names)
            .expectNext(listOf("J", "U", "N", "H", "E", "E"))
//            .expectNextCount(6)
            .verifyComplete()
    }

    @Test
    fun namesMonoFlatMapMany() {
        // given
        val strLen = 2L

        // when
        val names: Flux<String> = fluxMonoGenerator.namesMonoFlatMapMany(strLen).log()

        // then
        StepVerifier.create(names)
            .expectNext("J", "U", "N", "H", "E", "E")
            .verifyComplete()
    }

    @Test
    fun namesFluxTransform() {
        // given
        val strLen = 2L

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFluxTransform(strLen).log()

        // then
        StepVerifier.create(names)
            .expectNext("J", "U", "N", "H", "E", "E")
            .verifyComplete()
    }

    @Test
    fun namesFluxTransformDefaultIfEmpty() {
        // given
        val strLen = 3L

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFluxTransformDefaultIfEmpty(strLen).log()

        // then
        StepVerifier.create(names)
            .expectNext("default")
            .verifyComplete()
    }

    @Test
    fun namesFluxTransformSwitchIfEmpty() {
        // given
        val strLen = 3L

        // when
        val names: Flux<String> = fluxMonoGenerator.namesFluxTransformSwitchIfEmpty(strLen).log()

        // then
        StepVerifier.create(names)
            .expectNext("D", "E", "F", "A", "U", "L", "T")
            .verifyComplete()
    }

    @Test
    fun concat() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.concat().log()

        // then
        StepVerifier.create(names)
            .expectNext("a", "b", "c", "d", "e", "f")
            .verifyComplete()
    }

    @Test
    fun concatWith() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.concatWith().log()

        // then
        StepVerifier.create(names)
            .expectNext("a", "b", "c", "d", "e", "f")
            .verifyComplete()
    }

    @Test
    fun concatMono() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.concatMono().log()

        // then
        StepVerifier.create(names)
            .expectNext("a", "b")
            .verifyComplete()
    }

    @Test
    fun concatWithMono() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.concatWithMono().log()

        // then
        StepVerifier.create(names)
            .expectNext("a", "b")
            .verifyComplete()
    }

    @Test
    fun merge() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.merge().log()

        // then
        StepVerifier.create(names)
            .expectNext("A", "D", "B", "E", "C", "F")
            .verifyComplete()
    }

    @Test
    fun mergeWith() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.mergeWith().log()

        // then
        StepVerifier.create(names)
            .expectNext("A", "D", "B", "E", "C", "F")
            .verifyComplete()
    }

    @Test
    fun mergeWithMono() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.mergeWithMono().log()

        // then
        StepVerifier.create(names)
            .expectNext("A", "B")
            .verifyComplete()
    }

    @Test
    fun mergeSequential() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.mergeSequential().log()

        // then
        StepVerifier.create(names)
            .expectNext("A", "B", "C", "D", "E", "F")
            .verifyComplete()
    }

    @Test
    fun zip() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.zip().log()

        // then
        StepVerifier.create(names)
            .expectNext("AD", "BE", "CF")
            .verifyComplete()
    }

    @Test
    fun zip4() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.zip4().log()

        // then
        StepVerifier.create(names)
            .expectNext("AD14", "BE25", "CF36")
            .verifyComplete()
    }

    @Test
    fun zipMono() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.zipMono().log()

        // then
        StepVerifier.create(names)
            .expectNext("AB")
            .verifyComplete()
    }

    @Test
    fun zipWith() {
        // given

        // when
        val names: Flux<String> = fluxMonoGenerator.zipWith().log()

        // then
        StepVerifier.create(names)
            .expectNext("AD", "BE", "CF")
            .verifyComplete()
    }

    @Test
    fun zipWithMono() {
        // given

        // when
        val names: Mono<String> = fluxMonoGenerator.zipWithMono().log()

        // then
        StepVerifier.create(names)
            .expectNext("AB")
            .verifyComplete()
    }
}
