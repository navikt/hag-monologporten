package no.nav.helsearbeidsgiver.dialogporten

import kotlinx.coroutines.runBlocking
import no.nav.helsearbeidsgiver.Env
import no.nav.helsearbeidsgiver.kafka.Sykmelding
import no.nav.helsearbeidsgiver.kafka.Sykmeldingsperiode

class DialogportenService(
    private val dialogportenClient: DialogportenClient,
) {
    fun opprettNyDialogMedSykmelding(sykmelding: Sykmelding): String =
        runBlocking {
            dialogportenClient
                .opprettNyDialogMedSykmelding(
                    orgnr = sykmelding.orgnr,
                    dialogTittel = "Sykepenger for ${sykmelding.fulltNavn} (f. ${sykmelding.foedselsdato})",
                    dialogSammendrag = sykmelding.sykmeldingsperioder.getSykmeldingsPerioderString(),
                    sykmeldingId = sykmelding.sykmeldingId,
                    sykmeldingJsonUrl = "${Env.navArbeidsgiverApiBaseUrl}/sykmelding/${sykmelding.sykmeldingId}",
                )
        }

    private fun List<Sykmeldingsperiode>.getSykmeldingsPerioderString(): String =
        when (size) {
            1 -> "Sykmeldingsperiode ${first().fom} - ${first().tom}"
            else ->
                "Sykmeldingsperioder ${first().fom} - (...) - ${last().tom}"
        }
}
