package no.nav.helsearbeidsgiver.kafka

import no.nav.helsearbeidsgiver.Env
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

fun startKafkaConsumer(meldingTolker: MeldingTolker) {
    val consumer = KafkaConsumer<String, String>(createKafkaConsumerConfig() as Map<String, Any>)
    val topic = Env.Kafka.topic
    consumer.subscribe(listOf(topic))
    val running = true
    while (running) {
        val records = consumer.poll(Duration.ofMillis(1000))
        for (record in records) {
            meldingTolker.lesMelding(record.value())
            consumer.commitSync()
        }
    }
}
