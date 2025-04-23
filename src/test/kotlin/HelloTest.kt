import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class HelloTest :
    FunSpec({
        test("Hello test") {
            val helloWorld = "Hello world!"
            helloWorld shouldContain "Hello"
        }
    })
