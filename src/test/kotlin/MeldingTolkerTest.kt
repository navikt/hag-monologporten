import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import no.nav.helsearbeidsgiver.dialogporten.DialogportenService
import no.nav.helsearbeidsgiver.kafka.Melding
import no.nav.helsearbeidsgiver.kafka.MeldingTolker
import no.nav.helsearbeidsgiver.kafka.Sykmelding
import no.nav.helsearbeidsgiver.kafka.Sykmeldingsperiode
import no.nav.helsearbeidsgiver.utils.UnleashFeatureToggles
import no.nav.helsearbeidsgiver.utils.json.jsonConfig
import java.time.LocalDate
import java.util.UUID

class MeldingTolkerTest :
    FunSpec({
        test("Opprett dialog ved gyldig sykmelding") {
            val sykmelding = Sykmelding(
                sykmeldingId = UUID.randomUUID(),
                orgnr = "123456789",
                foedselsdato = LocalDate.of(1990, 1, 1),
                fulltNavn = "OLA NORDMANN",
                sykmeldingsperioder = listOf(
                    Sykmeldingsperiode(
                        fom = LocalDate.of(2023, 1, 1),
                        tom = LocalDate.of(2023, 1, 31),
                    ),
                ),
            )
            val melding = jsonConfig.encodeToString(Melding.serializer(), sykmelding)

            val dialogportenServiceMock = mockk<DialogportenService>()
            val unleashFeatureTogglesMock = mockk<UnleashFeatureToggles>()

            every { dialogportenServiceMock.opprettNyDialogMedSykmelding(any()) } returns "123"
            every { unleashFeatureTogglesMock.skalOppretteDialogVedMottattSykmelding() } returns true

            val meldingTolker =
                MeldingTolker(
                    unleashFeatureToggles = unleashFeatureTogglesMock,
                    dialogportenService = dialogportenServiceMock,
                )

            meldingTolker.lesMelding(melding)

            verify(exactly = 1) { dialogportenServiceMock.opprettNyDialogMedSykmelding(sykmelding) }
        }
    })
