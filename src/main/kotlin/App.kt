package no.nav.helsearbeidsgiver

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import no.nav.helsearbeidsgiver.kafka.startKafkaConsumer
import no.nav.helsearbeidsgiver.utils.UnleashFeatureToggles
import no.nav.helsearbeidsgiver.utils.log.sikkerLogger

fun main() {
    startServer()
}

fun startServer() {
    val sikkerLogger = sikkerLogger()

    sikkerLogger.info("Setter opp Unleash...")
    val unleashFeatureToggles = UnleashFeatureToggles()

    embeddedServer(
        factory = Netty,
        port = 8080,
        module = { startKafkaConsumer(unleashFeatureToggles) },
    ).start(wait = true)
}
