package no.nav.helsearbeidsgiver.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val norskDatoFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun LocalDate.tilNorskFormat(): String = format(norskDatoFormat)
