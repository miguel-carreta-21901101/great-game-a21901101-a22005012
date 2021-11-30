package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.util.List;


public class GameManager {

    //HashMap que vou manipulando
    HashMap<Integer, Programmer> programmers = new HashMap<>();
    //Hash que guarda os que caiem no blue screen
    HashMap<Integer, Programmer> programmersOutOfGame = new HashMap<>();
    // List Auxiliar para a funcao GameResults
    List<Programmer> programmerListGameResults = new ArrayList<>();
    //List Com o total de programmers iniciais
    List<Programmer> totalProgrammers = new ArrayList<>();
    //Lista de Ids dos programmers que uso para manipular
    List<Integer> idProgrammers = new ArrayList<>();
    // Hash< ID , ABYSS > de abismos
    HashMap<Integer, Abyss> abysses = new HashMap<>();
    // Hash< ID , TOOL > de ferramentas
    List<Tool> tools = new ArrayList<>();
    int idsCasas;
    boolean canCatch = false;
    Board board = new Board();
    Game game = new Game();


    public GameManager() {
    }

    //********************************************** GETTERS *************************************
    public String getImagePng(int position) {

        // position = pos no mapa

        if (position > board.getTamanho() || position < 1) {
            return null;
        }

        if (position == board.getTamanho()) {
            return board.getUltimaPosicaoDoMapa();

        }


        // Se houver um abismo na posicao POSITION , retorna a img que pertence
        for (Abyss abyss : abysses.values()) {
            if (abyss.getPos() == position) {

                switch (abyss.getId()) {

                    case 0:
                        return "syntax.png";

                    case 1:
                        return "logic.png";

                    case 2:
                        return "exception.png";

                    case 3:
                        return "file-not-found-exception.png";

                    case 4:
                        return "crash.png";

                    case 5:
                        return "duplicated-code.png";

                    case 6:
                        return "secondary-effects.png";

                    case 7:
                        return "bsod.png";

                    case 8:
                        return "infinite-loop.png";

                    case 9:
                        return "core-dumped.png";

                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        // Se houver uma tool na posicao POSITION , retorna a img que pertence
        for (Tool tool : tools) {
            if (tool.getPos() == position) {

                switch (tool.getId()) {

                    case 0:
                        return "inheritance.png";

                    case 1:
                        return "functional.png";

                    case 2:
                        return "unit-tests.png";

                    case 3:
                        return "catch.png";

                    case 4:
                        return "IDE.png";

                    case 5:
                        return "ajuda-professor.png";

                    default:
                        throw new IllegalArgumentException();

                }
            }
        }

        //Se o programmer estiver na posicao POSITION , retorna a imagem do player
        for (Programmer p : programmers.values()) {
            if (p.getPos() == position) {
                return "player" + p.getColor() + ".png";
            }
        }

        return null;

    }

    public String getTitle(int position) {

        if (position > board.getTamanho() || position < 1) {
            return null;
        }

        for (Abyss a : abysses.values()) {
            if (a.getPos() == position) {
                return a.toString();
            }
        }


        for (Tool t : tools) {
            if (t.getPos() == position) {
                return t.toString();
            }
        }

        return null;
    }

    public List<Programmer> getProgrammers(boolean includeDefeated) {

        //Devolve os values do hashmap programmers
        if (includeDefeated) {
            List<Programmer> allProgrammers = new ArrayList<>(programmers.values());
            allProgrammers.addAll(programmersOutOfGame.values());
            return allProgrammers;
        }
        List<Programmer> programmersAlive = new ArrayList<>();


        for (Programmer p : programmers.values()) {
            if (!p.isOutOfGame()) {
                programmersAlive.add(p);
            }
        }

        return programmersAlive;

    }

    public List<Programmer> getProgrammers(int position) {

        List<Programmer> programmers = new ArrayList<>();

        if (position < 0 || position > board.getTamanho()) {
            return null;
        }

        for (Programmer p : this.programmers.values()) {

            if (p.getPos() == position) {
                programmers.add(p);
            }
        }

        return programmers;
    }

    public String getProgrammersInfo() {

        StringBuilder info = new StringBuilder();

        for (Integer i : idProgrammers) {
            Programmer p = programmers.get(i);

            if (p != null) {


                info.append(p.getName()).append(" : ");

                if (p.getTools().size() == 0) {
                    info.append("No tools | ");

                } else {

                    StringBuilder auxBuilder = new StringBuilder();

                    for (Tool tool : p.getTools()) {
                        auxBuilder.append(tool);
                        auxBuilder.append(";");
                    }

                    info.append(auxBuilder.substring(0, auxBuilder.length() - 1));

                    info.append(" | ");
                }
            }

        }
        return info.substring(0, info.length() - 3);
    }

    public int getCurrentPlayerID() {
        return game.getCurrentPlayerID();
    }
    //**********************************************************************************************


    public void resetGame() {
        programmerListGameResults.clear();
        idProgrammers.clear();
        programmers.clear();
        abysses.clear();
        tools.clear();
        game = new Game();
        board = new Board();

    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {

        // Reset ao jogo
        resetGame();

        HashSet<Integer> idsRepetidos = new HashSet<>();
        HashSet<String> coresRepetidas = new HashSet<>();
        int countPlayers = 0;
        int idPlayers = 0;
        String nome = "";
        String linguagensProgramacao = "";
        String cor = "";


        if (worldSize < 1) {
            return false;
        }

        int i, j;


        for (i = 0; i < playerInfo.length; i++) {
            if (playerInfo[0] == null) {
                return false;
            }

            //Percorre as informaçoes de cada programador
            for (j = 0; j <= 3; j++) {

                //Se J for null E o num players inferior a 2, false
                if (playerInfo[i][j] == null && countPlayers < 2) {
                    return false;

                    //Se J for null E tiver o num players suficiente , true
                } else if (playerInfo[i][j] == null && countPlayers >= 2) {
                    return true;
                }

                switch (j) {

                    // ID
                    case 0:

                        // Se o ID  for repetido OU  menor que 0 , false
                        if (idsRepetidos.contains(Integer.parseInt(playerInfo[i][j])) ||
                                (Integer.parseInt(playerInfo[i][j]) < 0)) {

                            return false;
                        }
                        //adiciona o ID ao Hashset para validar se ha repetidos
                        idsRepetidos.add(Integer.parseInt(playerInfo[i][j]));
                        idPlayers = Integer.parseInt(playerInfo[i][j]);

                        //Adiciono ao array de ids para facilitar na escolha do CurrentPlayer mais à frente
                        idProgrammers.add(idPlayers);

                        break;

                    // NOME
                    case 1:

                        // Se o NOME for null OU vazio , false
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0) {
                            return false;
                        }
                        nome = playerInfo[i][j];

                        break;

                    // LISTA LINGUAGENS DE PROGRAMACAO
                    case 2:
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0) {
                            return false;
                        }

                        linguagensProgramacao = AuxFunctions.ordernarLinguagensProgramacao(playerInfo[i][j]);

                        break;

                    // COR
                    case 3:

                        //Se a cor for diferente das possiveis, false
                        if (!(playerInfo[i][j].equals("Purple") || playerInfo[i][j].equals("Green") ||
                                playerInfo[i][j].equals("Brown") || playerInfo[i][j].equals("Blue"))) {
                            return false;

                        }
                        // Se existir uma repetida, false
                        if (coresRepetidas.contains(playerInfo[i][j])) {
                            return false;
                        }

                        //Adiciona a cor ao HashSet para validar as cores repetidas
                        coresRepetidas.add(playerInfo[i][j]);
                        cor = playerInfo[i][j];
                        break;

                    default:
                        break;

                }
            }

            countPlayers++;

            // Crio um obj ProgrammerColor  e adiciono ao hashmap

            switch (cor) {
                case "Purple": {
                    ProgrammerColor color = ProgrammerColor.PURPLE;
                    programmers.put(idPlayers, new Programmer(idPlayers, nome, linguagensProgramacao, color));

                    break;
                }
                case "Blue": {
                    ProgrammerColor color = ProgrammerColor.BLUE;
                    programmers.put(idPlayers, new Programmer(idPlayers, nome, linguagensProgramacao, color));

                    break;
                }
                case "Green": {
                    ProgrammerColor color = ProgrammerColor.GREEN;
                    programmers.put(idPlayers, new Programmer(idPlayers, nome, linguagensProgramacao, color));

                    break;
                }
                case "Brown": {
                    ProgrammerColor color = ProgrammerColor.BROWN;
                    programmers.put(idPlayers, new Programmer(idPlayers, nome, linguagensProgramacao, color));
                    break;
                }

                default:
                    break;

            }


        }

        totalProgrammers.addAll(programmers.values());
        // O num de players entre 2 - 4
        if (countPlayers <= 1 || countPlayers > 4) {
            return false;
        }

        // o tamanho do board tem que ser no minimo 2 peças por player .
        if (worldSize < (countPlayers * 2)) {
            return false;
        }

        //              ABYSSES & TOOLS


        int idAbyss = 0;
        int idTool = 0;

        int posAbyss = 0;
        int posTool = 0;

        for (i = 0; i < abyssesAndTools.length; i++) {

            if (abyssesAndTools[i] == null) {
                return false;
            }


            // Var para detetar se é Abyss
            boolean abyssAlert = false;

            for (j = 0; j <= 2; j++) {

                if (abyssesAndTools[i][j] == null) {
                    return false;
                }

                switch (j) {

                    // 0 -> Abismo  ||  1 -> Ferramenta
                    case 0:

                        // se nao for nenhum dos dois , false
                        if (!abyssesAndTools[i][0].equals(String.valueOf(0)) && !abyssesAndTools[i][0]
                                .equals(String.valueOf(1))) {

                            return false;

                        }

                        // Se for 0 (abismo) o detetor da true
                        if (abyssesAndTools[i][0].equals(String.valueOf(0))) {
                            abyssAlert = true;
                        }
                        break;

                    case 1:
                        if (abyssesAndTools[i][1] == null) {
                            return false;
                        }
                        if (abyssAlert) {
                            if (Integer.parseInt(abyssesAndTools[i][1]) < 0 ||
                                    Integer.parseInt(abyssesAndTools[i][1]) > 9) {
                                return false;
                            }
                            idAbyss = Integer.parseInt(abyssesAndTools[i][1]);
                            break;
                        }

                        if (Integer.parseInt(abyssesAndTools[i][1]) < 0 ||
                                Integer.parseInt(abyssesAndTools[i][1]) > 5) {
                            return false;
                        }
                        idTool = Integer.parseInt(abyssesAndTools[i][1]);
                        break;

                    case 2:
                        if (abyssAlert) {
                            posAbyss = Integer.parseInt(abyssesAndTools[i][2]);
                            break;
                        }
                        posTool = Integer.parseInt(abyssesAndTools[i][2]);
                        break;

                    default:
                        break;

                }

                if (abyssAlert) {
                    abysses.put(idAbyss, new Abyss(idAbyss, AuxFunctions.setTitleAbyss(idAbyss), posAbyss));
                    continue;
                }

                tools.add(new Tool(idTool, AuxFunctions.setTitleTool(idTool), posTool));


            }

        }

        // Ordeno o array de IDS para saber que está por ordem do mais pequeno para o maior
        Collections.sort(idProgrammers);

        //Declaro que o primeiro player a jogar é o que está em primeiro lugar , pois está ordenado do menor -> maior
        game.setCurrentPlayerID(idProgrammers.get(0));

        //Declaro o tamanho do mapa
        board.setTamanho(worldSize);

        return true;

    }


    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {


        // Reset ao jogo
        resetGame();

        HashSet<Integer> idsRepetidos = new HashSet<>();
        HashSet<String> coresRepetidas = new HashSet<>();
        int countPlayers = 0;
        int id = 0;
        String nome = "";
        String linguagensProgramacao = "";
        String cor = "";

        if (worldSize < 1) {
            return false;
        }

        int i, j;


        for (i = 0; i < playerInfo.length; i++) {
            if (playerInfo[0] == null) {
                return false;
            }

            //Percorre as informaçoes de cada programador
            for (j = 0; j <= 3; j++) {

                //Se J for null E o num players inferior a 2, false
                if (playerInfo[i][j] == null && countPlayers < 2) {
                    return false;

                    //Se J for null E tiver o num players suficiente , true
                } else if (playerInfo[i][j] == null && countPlayers >= 2) {
                    return true;
                }

                switch (j) {

                    // ID
                    case 0:

                        // Se o ID  for repetido OU  menor que 0 , false
                        if (idsRepetidos.contains(Integer.parseInt(playerInfo[i][j])) ||
                                (Integer.parseInt(playerInfo[i][j]) < 0)) {

                            return false;
                        }
                        //adiciona o ID ao Hashset para validar se ha repetidos
                        idsRepetidos.add(Integer.parseInt(playerInfo[i][j]));
                        id = Integer.parseInt(playerInfo[i][j]);

                        //Adiciono ao array de ids para facilitar na escolha do CurrentPlayer mais à frente
                        idProgrammers.add(id);

                        break;

                    // NOME
                    case 1:

                        // Se o NOME for null OU vazio , false
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0) {
                            return false;
                        }
                        nome = playerInfo[i][j];

                        break;

                    // LISTA LINGUAGENS DE PROGRAMACAO
                    case 2:
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0) {
                            return false;
                        }

                        linguagensProgramacao = AuxFunctions.ordernarLinguagensProgramacao(playerInfo[i][j]);

                        break;

                    // COR
                    case 3:

                        //Se a cor for diferente das possiveis, false
                        if (!(playerInfo[i][j].equals("Purple") || playerInfo[i][j].equals("Green") ||
                                playerInfo[i][j].equals("Brown") || playerInfo[i][j].equals("Blue"))) {
                            return false;

                        }
                        // Se existir uma repetida, false
                        if (coresRepetidas.contains(playerInfo[i][j])) {
                            return false;
                        }

                        //Adiciona a cor ao HashSet para validar as cores repetidas
                        coresRepetidas.add(playerInfo[i][j]);
                        cor = playerInfo[i][j];
                        break;

                    default:
                        break;

                }
            }

            countPlayers++;

            // Crio um obj ProgrammerColor  e adiciono ao hashmap

            switch (cor) {
                case "Purple": {
                    ProgrammerColor color = ProgrammerColor.PURPLE;
                    programmers.put(id, new Programmer(id, nome, linguagensProgramacao, color));

                    break;
                }
                case "Blue": {
                    ProgrammerColor color = ProgrammerColor.BLUE;
                    programmers.put(id, new Programmer(id, nome, linguagensProgramacao, color));

                    break;
                }
                case "Green": {
                    ProgrammerColor color = ProgrammerColor.GREEN;
                    programmers.put(id, new Programmer(id, nome, linguagensProgramacao, color));

                    break;
                }
                case "Brown": {
                    ProgrammerColor color = ProgrammerColor.BROWN;
                    programmers.put(id, new Programmer(id, nome, linguagensProgramacao, color));
                    break;
                }

                default:
                    break;

            }


        }

        // O num de players entre 2 - 4
        if (countPlayers <= 1 || countPlayers > 4) {
            return false;
        }

        // o tamanho do board tem que ser no minimo 2 peças por player .
        if (worldSize < (countPlayers * 2)) {
            return false;
        }


        // Ordeno o array de IDS para saber que está por ordem do mais pequeno para o maior
        Collections.sort(idProgrammers);

        //Declaro que o primeiro player a jogar é o que está em primeiro lugar , pois está ordenado do menor -> maior
        game.setCurrentPlayerID(idProgrammers.get(0));

        //Declaro o tamanho do mapa
        board.setTamanho(worldSize);

        return true;

    }


    public boolean moveCurrentPlayer(int nrSpaces) {

        // numeros do dado 1 - 6
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        int aux;
        int idAbyss;

        // Replico o programmer que esta neste momento a jogar
        Programmer programmerTemp = programmers.get(getCurrentPlayerID());


        if (programmerTemp.isOutOfGame() || programmerTemp.isStuck()) {
            return false;
        }


        if ((programmerTemp.getPos() + nrSpaces) > board.getTamanho()) {


            // Recua o numero de casas em excesso
            aux = (board.getTamanho() - programmerTemp.getPos() - nrSpaces) * -1;


            //Adiciono ao verdadeiro programmer a sua posicao atual
            programmers.get(programmerTemp.getId()).setPos(board.getTamanho() - aux);

        } else {

            // Insiro na posAux a casa onde o player irá parar
            int posAux = programmerTemp.getPos() + nrSpaces;
            canCatch = false;

            // Se houver uma tool nessa casa E o player ainda nao tenha adquirido esta tool , ele apanha a tool.
            if (AuxFunctions.isTool(tools, posAux)) {
                if (programmerTemp.catchTool(AuxFunctions.setTool(tools, posAux))) {
                    programmerTemp.catchTool(AuxFunctions.setTool(tools, posAux));
                    canCatch = true;
                }


                // (Se existir uma tool na casa   E   o player já a tenha na sua Lista de Tools )   OU   (Não exista
                // uma tool na casa) , ele nao faz absolutamente nada.
            }

            // Se existir um abismo na casa
            if (AuxFunctions.isAbyss(abysses, posAux)) {
                idAbyss = AuxFunctions.setIdAbyss(abysses, posAux);

                //  System.out.println("ABISMO POS : " + posAux);

                switch (idAbyss) {


                    // ERRO SINTAX -  Recya 1 casa
                    case 0:
                        idsCasas = 0;
                        break;


                    case 1:
                        idsCasas = 1;
                        game.setDiceShoot(nrSpaces);
                        break;


                    case 2:
                        idsCasas = 2;
                        break;


                    case 3:
                        idsCasas = 3;
                        break;


                    case 4:
                        idsCasas = 4;
                        break;


                    // DUPLICATED CODE
                    // Recua até à casa onde estava antes de chegar a esta casa.
                    case 5:
                        idsCasas = 5;

                        break;


                    // EFEITOS SECUNDARIOS
                    //recua para a posição onde estava há 2 movimentos atrás
                    case 6:

                        idsCasas = 6;
                        break;


                    //Blue Screen  -   perde imediatamente o jogo
                    case 7:
                        idsCasas = 7;
                        break;


                    // CICLO INFINITO
                    case 8:
                        idsCasas = 8;
                        break;
                    // CORE DUMPED
                    case 9:
                        idsCasas = 9;
                        break;

                    default:
                        break;

                }


            }

            AuxFunctions.changePosAndCasa(posAux, programmers, programmerTemp);

        }


        return true;
    }

    public String reactToAbyssOrTool() {

        boolean isTool = false;
        boolean isAbyss = false;
        Programmer programmerTemp = programmers.get(game.getCurrentPlayerID());

       // System.out.println(programmerTemp.getPos());
        for (Tool tool : tools) {
            if (programmerTemp.getPos() == tool.getPos()) {
                isTool = true;
                break;
            }
        }

        if (!programmerTemp.isStuck()) {
            if (!isTool) {

                for (Abyss abyss : abysses.values()) {
                    if (programmers.containsKey(programmerTemp.getId())) {

                        if (programmerTemp.getPos() == abyss.getPos()) {
                            isAbyss = true;
                            if (programmerTemp.isStuck()) {
                                break;
                            }
                            List<Programmer> programmersInThisPositions;
                            boolean counterAbyss = false;

                            switch (idsCasas) {


                                // SYNTAX - Recua 1 casa
                                case 0:
                                    for (Tool tool : programmerTemp.getTools()) {
                                        if (tool.getId() == 5 || tool.getId() == 4) {
                                            counterAbyss = true;
                                            programmerTemp.dropTool(tool);
                                            break;
                                        }
                                    }
                                    if (!counterAbyss) {
                                        AuxFunctions.changePosAndCasa(programmerTemp.getPos() - 1,
                                                programmers, programmerTemp);
                                    }
                                    break;


                                //LOGICA - Recua metado do valor que tiver saido no dado
                                case 1:
                                    for (Tool tool : programmerTemp.getTools()) {
                                        if (tool.getId() == 5 || tool.getId() == 2) {
                                            counterAbyss = true;
                                            programmerTemp.dropTool(tool);
                                            break;
                                        }
                                    }

                                    if (!counterAbyss) {
                                        double auxSpaces = (double) game.getCurrentDiceShoot() / 2;
                                        AuxFunctions.changePosAndCasa(programmerTemp.getPos() -
                                                        (int) Math.floor(auxSpaces),
                                                programmers, programmerTemp);
                                    }
                                    break;


                                //EXCEPTION - recua 2 casas
                                case 2:
                                    for (Tool tool : programmerTemp.getTools()) {
                                        if (tool.getId() == 5 || tool.getId() == 3) {
                                            counterAbyss = true;
                                            programmerTemp.dropTool(tool);
                                            break;
                                        }
                                    }
                                    if (!counterAbyss) {


                                        AuxFunctions.changePosAndCasa(programmerTemp.getPos() - 2,
                                                programmers, programmerTemp);
                                    }
                                    break;


                                // FILE NOT FOUND - recua 3 casas
                                case 3:
                                    for (Tool tool : programmerTemp.getTools()) {
                                        if (tool.getId() == 5 || tool.getId() == 3) {
                                            counterAbyss = true;
                                            programmerTemp.dropTool(tool);
                                            break;
                                        }
                                    }

                                    if (!counterAbyss) {
                                        AuxFunctions.changePosAndCasa(programmerTemp.getPos() - 3,
                                                programmers, programmerTemp);
                                    }
                                    break;


                                //CRASH - volta à primeira casa
                                case 4:

                                    AuxFunctions.changePosAndCasa(1, programmers, programmerTemp);

                                    break;


                                // DUPLICATED CODE - Recuar ate à antiga casa onde o plauer estava
                                case 5:

                                    for (Tool tool : programmerTemp.getTools()) {
                                        if (tool.getId() == 0) {
                                            counterAbyss = true;
                                            programmerTemp.dropTool(tool);
                                            break;
                                        }
                                    }

                                    if (!counterAbyss) {
                                        AuxFunctions.changePosAndCasa(programmerTemp.getCasasPercorridasList().get(
                                                programmerTemp.getCasasPercorridasList().size() - 2), programmers,
                                                programmerTemp);
                                    }
                                    break;


                                // EFEITOS SECUNDARIOS
                                case 6:
                                    for (Tool tool : programmerTemp.getTools()) {
                                        if (tool.getId() == 1) {
                                            counterAbyss = true;
                                            programmerTemp.dropTool(tool);
                                            break;
                                        }
                                    }
                                    if (!counterAbyss) {
                                        AuxFunctions.changePosAndCasa(programmerTemp.getCasasPercorridasList().get(
                                                programmerTemp.getCasasPercorridasList().size() - 3), programmers,
                                                programmerTemp);
                                    }
                                    break;

                                // BLUE SCREEN - perde imediatamente o jogo
                                case 7:

                                    int indexAuxToRemoveID = 0;
                                    for (Integer i : idProgrammers) {
                                        if (i == game.getCurrentPlayerID()) {
                                            break;
                                        }
                                        indexAuxToRemoveID++;
                                    }

                                    programmerListGameResults.add(programmerTemp);
                                    programmers.get(game.getCurrentPlayerID()).setOutOfGame();

                                    programmersOutOfGame.put(game.getCurrentPlayerID(),
                                            programmers.get(game.getCurrentPlayerID()));



                                    idProgrammers.remove(indexAuxToRemoveID);
                                    // programerList.remove(programmers.get(game.getCurrentPlayerID()));
                                    programmers.remove(game.getCurrentPlayerID());
                                    game.removeOneCount();
                                    //game.count--;


                                    break;


                                case 8:

                                    // Ciclo Infinito
                                    for (Tool tool : programmerTemp.getTools()) {
                                        if (tool.getId() == 1) {
                                            counterAbyss = true;
                                            programmerTemp.dropTool(tool);
                                            break;
                                        }
                                    }
                                    int count = 0;
                                    if (!counterAbyss) {
                                        programmerTemp.stuckedByInfiniteCircle();

                                        programmersInThisPositions = getProgrammers(programmerTemp.getPos());
                                        if (programmersInThisPositions.size() > 1) {
                                            for (Programmer p : programmersInThisPositions) {
                                                if (!p.isStuck()) {
                                                    programmersInThisPositions.remove(count);
                                                }
                                                count++;
                                                if (p.getId() == programmerTemp.getId()) {
                                                    continue;
                                                }
                                                p.freeFromInfiniteCircle();
                                            }
                                        }
                                    }
                                    break;


                                case 9:

                                    programmersInThisPositions = getProgrammers(programmerTemp.getPos());

                                    if (programmersInThisPositions.size() > 1) {
                                        for (Programmer p : programmersInThisPositions) {
                                            AuxFunctions.changePosAndCasa(p.getPos() - 3, programmers, p);
                                        }
                                    }

                                    break;


                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }

       /*if (programmerTemp.getPos() >= 31) {
            programmerTemp.setPos(1);
        }
*/
        // Incremento o count para ir buscar o proximo posicao no array IDS
        game.addOneCount();

        // Se o count chegar ao ids.size , comeca de novo para nao dar index out of bound
        if (game.getCount() >= idProgrammers.size()) {
            game.setCount(0);

        }

        //Declaro o proximo jogador a jogar
        game.setCurrentPlayerID(idProgrammers.get(game.getCount()));


        //Troco de turno incrementando os turnos terminados
        game.nextShift();

        //System.out.println(programmerTemp);


        if (isTool && !canCatch) {

          //  System.out.println("Já tens isto zé");
            return "Já tens esta ferramenta, siga para a próxima !";


        } else if (isTool) {
          //  System.out.println("TOOL");
            return "Parabéns, apanhaste uma ferramenta !!";

        } else if (isAbyss) {
          //  System.out.println("ABISMO");
            return "Ohhh, caiste num abismo !!";

        } else if (programmerTemp.isStuck()) {
         //  System.out.println("PRESO");
            return "Estás preso em Ciclos Infinitos! Pode ser que tenhas sorte e alguém te tire daí !";

        }
       // System.out.println("NULL");
        return null;


    }


    public boolean gameIsOver() {

        // Se algum jogador chegar à ultima casa do mapa ,
        for (Programmer programmer : programmers.values()) {
            if (programmer.getPos() == board.getTamanho() || programmers.size() <= 1) {

                programmerListGameResults.addAll(programmers.values());

                //Declaro o Vencedor
                game.setWinner(programmer.getName());
                return true;
            }
        }

        return false;
    }

    public List<String> getGameResults() {

        ArrayList<String> gameResults = new ArrayList<>();

        try {

            gameResults.add("O GRANDE JOGO DO DEISI");
            gameResults.add("");
            gameResults.add("NR. DE TURNOS");
            gameResults.add("" + game.getEndedShifts());
            gameResults.add("");
            gameResults.add("VENCEDOR");
            gameResults.add(game.getWinner());
            gameResults.add("");
            gameResults.add("RESTANTES");


            programmerListGameResults.sort((p1, p2) -> {
                if (p1.getPos() < p2.getPos()) {
                    return 1;
                } else if (p1.getPos() > p2.getPos()) {
                    return -1;
                } else {
                    return p1.getName().compareTo(p2.getName());
                }
            });

            for (Programmer i : programmerListGameResults) {
                //se o nome do programer é o winner, passo à frente
                if (i.getName().equals(game.getWinner())) {
                    continue;
                }

                gameResults.add(i.getName() + " " + i.getPos());
            }

        } catch (Exception e) {

            gameResults.clear();

        }


        return gameResults;
    }

    public JPanel getAuthorsPanel() {

        JPanel painel = new JPanel();

        painel.setPreferredSize(new Dimension(300, 300));
        painel.setBackground(Color.LIGHT_GRAY);
        JLabel miguel = new JLabel("Miguel Carreta     21901101");
        JLabel traco = new JLabel("_________________________________________");
        JLabel ricardo = new JLabel("Ricardo Gonçalves 22005012");
        painel.add(miguel);
        painel.add(traco);
        painel.add(ricardo);

        return painel;
    }

}