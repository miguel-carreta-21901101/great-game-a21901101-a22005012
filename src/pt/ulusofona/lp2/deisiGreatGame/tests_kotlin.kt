package pt.ulusofona.lp2.deisiGreatGame

import org.junit.Test
import java.util.Comparator

class tests_kotlin {
    fun polyglots2(manager: GameManager): String? {

        var programer1 : Programmer = Programmer(1 , "JEFF", "Java,C,Kotlin", ProgrammerColor.BLUE)
        var programer3 : Programmer = Programmer(1 , "MARRY", "Java", ProgrammerColor.BLUE)
        var programer2 : Programmer = Programmer(2 , "STEVE", "Java,C", ProgrammerColor.BLUE)
        manager.programmers.put(1, programer1)
        manager.programmers.put(2, programer2)
        manager.programmers.put(3, programer3)

        var stringFinal : String = ""

        manager.getProgrammers(true)
            .filter { it.numeroLinguas > 1 }
            .sortedBy {  it.numeroLinguas }
            .reversed()
            .forEach({stringFinal += it.name + ":" + it.numeroLinguas + "\n"})

        return stringFinal
    }

    fun mostUsedAbysses(manager: GameManager, max_results : Int): String? {
        //obter os abysses
        //saber quantas x foram pisadas
        //fazer um dic para cada tipo com cada x que esse tipo foi pisado
        // iterar e construir o dic
        //filtrar etc

        manager.steppedOnSyntaxError = 7
        manager.steppedOnLogicError = 4
        manager.steppedOnBlueScreenDeath = 3

        var stringFinal : String = ""

        var res = manager.steppedOn
            .entries.sortedBy { it.value }
            .reversed().take(max_results).associate { it.toPair() }

        res.forEach({stringFinal += it.key + ":" + it.value + "\n"})

        return stringFinal
    }

    @Test
    fun main() {
        var manager: GameManager = GameManager()
        val test = polyglots2(manager)
        println(test)

        val test2 = mostUsedAbysses(manager, 4)
        println(test2)
    }

}