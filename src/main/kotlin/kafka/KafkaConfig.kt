package no.nav.helsearbeidsgiver.kafka

import no.nav.helsearbeidsgiver.Env
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.Properties

fun createKafkaConsumerConfig(): Properties {
    val consumerKafkaProperties =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to Env.Kafka.kafkaBrokers,
            ConsumerConfig.GROUP_ID_CONFIG to "helsearbeidsgiver-dialog",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to "1",
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to "30000",
            ConsumerConfig.CLIENT_ID_CONFIG to "dialog",
        )
    return Properties().apply { putAll(consumerKafkaProperties + commonKafkaProperties()) }
}

private fun commonKafkaProperties(): Map<String, String> {
    val pkcs12 = "PKCS12"
    val javaKeyStore = "jks"

    val truststoreConfig =
        Env
            .Kafka.kafkaKeystorePath
            .let {
                mapOf(
                    SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG to it,
                    CommonClientConfigs.SECURITY_PROTOCOL_CONFIG to SecurityProtocol.SSL.name,
                    SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG to "",
                    SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG to javaKeyStore,
                    SslConfigs.SSL_KEYSTORE_TYPE_CONFIG to pkcs12,
                )
            }

    val credstoreConfig =
        Env
            .Kafka.kafkaCredstorePassword
            .let {
                mapOf(
                    SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG to it,
                    SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG to it,
                    SslConfigs.SSL_KEY_PASSWORD_CONFIG to it,
                )
            }

    val keystoreConfig =
        Env
            .Kafka.kafkaCredstorePath
            .let {
                mapOf(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG to it)
            }

    return truststoreConfig + credstoreConfig + keystoreConfig
}
