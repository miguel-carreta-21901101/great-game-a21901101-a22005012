package pt.ulusofona.lp2.deisiGreatGame

import pt.ulusofona.lp2.deisiGreatGame.CommandType.*

enum class CommandType { GET, POST }

fun router(): (CommandType) -> (GameManager, List<String>) -> String? {
    return { commandType -> wichCommand(commandType) }
}

fun wichCommand(commandType: CommandType): (GameManager, List<String>) -> String? {

    when (commandType) {
        GET -> return { game, values -> functionGet(game, values) }
        POST -> return { game, values -> functionPost(game, values) }
    }
}


fun functionGet(game: GameManager, list: List<String>): String? {
    when (list[0]) {
        "PLAYER" -> return player(game, list)
        "PLAYERS_BY_LANGUAGE" -> return playersByLanguage(game, list[1])
        "POLYGLOTS" -> return polyglots(game)
        "MOST_USED_POSITIONS" -> return mostUsedPositions(game, list)
        "MOST_USED_ABYSSES" -> return mostUsedAbysses(game, list[1].toInt())
    }
    return null
}

fun functionPost(game: GameManager, list: List<String>): String? {
    when (list[0]) {
        "MOVE" ->  return move(game, list)
        "ABYSS" ->  return postAbyss(game, list[1].toInt(), list[2].toInt())
    }
    return null
}

fun player(manager: GameManager, playerName: List<String>): String {

    return manager.programmers.values.filter { it.name == playerName[1] }.joinToString { it.toString() }.ifEmpty {
        "Inexistent player"
    }
}


fun playersByLanguage(manager: GameManager, wantedLanguage: String): String {

    var stringFinal : String = ""

    manager.programmers.values
        .filter { wantedLanguage in it.linguagens.split("; ")}
        .forEach { stringFinal += it.name + ',' }
    return stringFinal.dropLast(1)
}

fun polyglots(manager: GameManager): String {

    var stringFinal : String = ""

    manager.programmers.values
        .filter { it.obtainNumeroLinguas() > 1 }
        .sortedBy {  it.obtainNumeroLinguas() }
        .forEach { stringFinal += it.name + ":" + it.obtainNumeroLinguas() + "\n" }

    return stringFinal.trim()
}

fun mostUsedPositions(manager: GameManager, list: List<String>): String {
    return manager
        .casasMaisPisadas.map { it.key to it.value }
        .sortedByDescending { it.second }
        .take(Integer.parseInt(list[1]))
        .joinToString("\n"){""+ it.first + ":" + it.second}
}
/*
fun mostUsedAbysses(manager: GameManager, list: List<String>): String {
    return manager
        .abyssesMaisPisados.map { it.key to it.value }
        .sortedByDescending { it.second }
        .take(Integer.parseInt(list[1]))
        .joinToString("\n"){""+ it.first + ":" + it.second}
}*/


fun mostUsedAbysses(manager: GameManager, max_results : Int): String {
    var stringFinal : String = ""

    var res = manager.steppedOn
        .entries.sortedByDescending{ it.value }
        .take(max_results - 1).associate { it.toPair() }

    res.forEach { stringFinal += it.key + ":" + it.value + "\n" }

    return stringFinal.trim().trim()
}

fun move(manager: GameManager, pos: List<String>): String {
    manager.moveCurrentPlayer(Integer.parseInt(pos[1]))
    return if (manager.reactToAbyssOrTool() == null) {
        "OK"
    } else {
        "Abyss Or Tool"
    }
}

fun postAbyss(manager: GameManager, abyssId : Int , posWanted: Int): String {

    if(manager.abysses.filter { it.pos == posWanted }.isNotEmpty()){
        return "Position is occupied"
    }

    manager.abysses.add(Abyss.createAbyss(abyssId, AuxCode.setTitleAbyss(abyssId), posWanted))

    return "OK"
}
