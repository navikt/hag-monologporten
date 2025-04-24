package no.nav.helsearbeidsgiver

object Env {
    object Unleash {
        val apiKey = "UNLEASH_SERVER_API_TOKEN".fromEnv()
        val apiUrl = "UNLEASH_SERVER_API_URL".fromEnv()
        val apiEnv = "UNLEASH_SERVER_API_ENV".fromEnv()
    }

    object Kafka {
        val topic = "KAFKA_TOPIC".fromEnv()
        val kafkaBrokers = "KAFKA_BROKERS".fromEnv()
        val kafkaCredstorePath = "KAFKA_CREDSTORE_PATH".fromEnv()
        val kafkaKeystorePath = "KAFKA_KEYSTORE_PATH".fromEnv()
        val kafkaCredstorePassword = "KAFKA_CREDSTORE_PASSWORD".fromEnv()
    }

    fun String.fromEnv(): String =
        System.getenv(this)
            ?: throw RuntimeException("Missing required environment variable \"$this\".")
}
