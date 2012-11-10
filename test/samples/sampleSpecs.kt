package samples

import spek.spec
import spek.given
import spek.SpecificationRunner
import java.util.ArrayList

import spek.*

//import spek.shouldEqual

fun main(args: Array<String>) {
    val textOutput = ConsoleTextListener()
    val listeners = ArrayList<Listener>()
    val multipleNotifiers = MultipleListenerNotifier(listeners)

    listeners.add(textOutput)
    val specRunner = SpecificationRunner(multipleNotifiers)
    try {
        specRunner.runSpecsInFolder("samples")
    } finally {
        specRunner.close()
    }
}

spec public fun calculatorSpecs() {


    given("a calculator")
    {
        val calculator = Calculator()

        on("calling sum with two numbers")
        {

            val sum = calculator.sum(2, 4)


            it("should return the result of adding the first number to the second number")
            {
                shouldEqual(6, sum)
            }
        }

        on("calling substract with two numbers")
        {
            val subtract = calculator.subtract(4, 2)

            it("should return the result of substracting the second number from the first number")
            {
                shouldEqual(2, subtract)
            }
        }
    }
}

spec fun incrementorSpecs() {
    given("an incrementor") {
        val incrementor = Incrementor()
        on("calling incVaueBy with 4 and given number 6") {
            val result = incrementor.incValueBy(4, 6)
            it("should return 10") {
                shouldEqual(result, 10)
            }
        }
    }
}

class Incrementor {
    fun incValueBy(value: Int, inc: Int) = value + inc
}

class Calculator {
    fun sum(x: Int, y: Int) = x + y
    fun subtract(x: Int, y: Int) = x - y
}
