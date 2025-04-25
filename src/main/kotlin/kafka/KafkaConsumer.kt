package no.nav.helsearbeidsgiver.kafka

import no.nav.helsearbeidsgiver.Env
import no.nav.helsearbeidsgiver.utils.UnleashFeatureToggles
import no.nav.helsearbeidsgiver.utils.log.sikkerLogger
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

fun startKafkaConsumer(unleashFeatureToggles: UnleashFeatureToggles) {
    val consumer = KafkaConsumer<String, String>(createKafkaConsumerConfig() as Map<String, Any>)
    val topic = Env.Kafka.topic
    consumer.subscribe(listOf(topic))
    val running = true
    while (running) {
        val records = consumer.poll(Duration.ofMillis(1000))
        for (record in records) {
            sikkerLogger().info("Consumed message: ${record.value()} from partition: ${record.partition()}")
            sikkerLogger().info(
                "Unleash feature toggle for dialogopprettelse: ${unleashFeatureToggles.skalOppretteDialogVedMottattSykmelding()}",
            )
            consumer.commitSync()
        }
    }
}
