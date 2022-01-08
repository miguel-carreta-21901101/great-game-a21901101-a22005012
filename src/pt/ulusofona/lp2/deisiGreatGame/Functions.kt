package pt.ulusofona.lp2.deisiGreatGame

import pt.ulusofona.lp2.deisiGreatGame.CommandType.*

enum class CommandType { GET, POST }

fun router(): (CommandType) -> (GameManager, List<String>) -> String? {
    return { commandType -> wichCommand(commandType) }
}

fun wichCommand(commandType: CommandType): (GameManager, List<String>) -> String? {

    when (commandType) {
        GET -> return { game, values -> functionGET(game, values) }
        POST -> return { game, values -> functionPOST(game, values) }
    }
}


fun functionGET(game: GameManager, list: List<String>): String? {
    when (list[0]) {
        "PLAYER" -> return player(game, list)
        "PLAYERS_BY_LANGUAGE" -> return playersByLanguage(game, list)
        "POLYGLOTS" -> return polyglots(game, list)
        "MOST_USED_POSITIONS" -> return mostUsedPositions(game, list)
        "MOST_USED_ABYSSES" -> return mostUsedAbysses(game, list)
    }
    return null
}

fun functionPOST(game: GameManager, list: List<String>): String? {
    when (list[0]) {
          "MOVE" ->  return move(game, list)
        //   "ABYSS" ->  return move(game, list)
    }
    return null
}

fun player(manager: GameManager, playerName: List<String>): String {

    return manager.programmers.values.filter { it.name == playerName[1] }.joinToString { it.toString() }.ifEmpty {
        "Inexistent player"
    }
}

fun playersByLanguage(manager: GameManager, languages: List<String>): String {
    return manager.programmers.values.filter { it.linguagens == languages[1] }.joinToString(",") { it.name }
        .ifBlank { return "" }
}

fun polyglots(manager: GameManager, list: List<String>): String? {
    var stringFinal : String = ""

    manager.getProgrammers(true)
        .filter { it.numeroLinguas > 1 }
        .sortedBy {  it.numeroLinguas }
        .reversed()
        .forEach({stringFinal += it.name + ": " + it.numeroLinguas + "\n"})

    return stringFinal
}

fun mostUsedPositions(manager: GameManager, list: List<String>): String? {
    return ""
}

fun mostUsedAbysses(manager: GameManager, max_results : Int): String? {
    var stringFinal : String = ""

    var res = manager.steppedOn
        .entries.sortedBy { it.value }
        .reversed().take(max_results).associate { it.toPair() }

    res.forEach({stringFinal += it.key + ":" + it.value + "\n"})

    return stringFinal
}

fun move(manager: GameManager, pos: List<String>): String {
    manager.moveCurrentPlayer(Integer.parseInt(pos[1]))
    return if (manager.reactToAbyssOrTool() == null) {
        "OK"
    } else {
        "Abyss Or Tool"
    }
}