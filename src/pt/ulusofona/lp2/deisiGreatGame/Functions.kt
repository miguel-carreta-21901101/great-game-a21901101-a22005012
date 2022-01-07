package pt.ulusofona.lp2.deisiGreatGame

import pt.ulusofona.lp2.deisiGreatGame.CommandType.*

enum class CommandType {GET, POST}

fun router(): (CommandType) -> (GameManager, List<String>) -> String ?{
    return {commandType -> wichCommand(commandType)}
}

fun wichCommand(commandType: CommandType): (GameManager, List<String>) -> String?{

    when(commandType) {
        GET -> return {game, values -> functionGET(game, values)}
        POST -> return {game, values -> functionPOST(game, values)}
    }
}


fun functionGET(game: GameManager, list: List<String>) : String?{
    when (list[0]){
        "PLAYER" -> return player(game, list)
     /*   "PLAYERS_BY_LANGUAGE" -> return playersByLanguage(game, list)
        "POLYGLOTS" -> return polyglots(game, list)
        "MOST_USED_POSITIONS" -> return player(game, list)
        "MOST_USED_ABYSSES" -> return player(game, list)*/
    }
    return null
}

fun functionPOST(game: GameManager, list : List<String>) : String?{
    when(list[0]){
      //  "MOVE" ->  return move(game, list)
     //   "ABYSS" ->  return move(game, list)
    }
    return null
}

fun player(manager : GameManager, playerName: List<String>) : String{

    return manager.programmers.values.filter { it.name == playerName[1] }.joinToString { it.toString() }.ifEmpty {
        "Inexistent player"
    }
}