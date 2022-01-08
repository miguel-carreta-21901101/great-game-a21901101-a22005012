package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    GameSetting gameSetting = new GameSetting();

    //Counter de steppedOn Abysses
    int steppedOnSyntaxError = 0;
    int steppedOnLogicError = 0;
    int steppedOnExceptionFault = 0;
    int steppedOnFileNotFoundException = 0;
    int steppedOnCrash = 0;
    int steppedOnDuplicatedCode = 0;
    int steppedOnSideEffect = 0;
    int steppedOnBlueScreenDeath = 0;
    int steppedOnInfiniteLoop = 0;
    int steppedOnSegmentationFault = 0;

    private String[][] playerInfoLoadGame = null;


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
                return abyss.getImagePng();
            }
        }

        // Se houver uma tool na posicao POSITION , retorna a img que pertence
        for (Tool tool : tools) {
            if (tool.getPos() == position) {
                return tool.getImagePng();

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

        for (Abyss abyss : abysses.values()) {
            if (abyss.getPos() == position) {
                return abyss.toString();
            }
        }


        for (Tool tool : tools) {
            if (tool.getPos() == position) {
                return tool.toString();
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
        return gameSetting.getCurrentPlayerID();
    }
    //**********************************************************************************************

    private void writePlayer(FileWriter writer, Programmer programmer) throws IOException {
        boolean temTool = false;

        writer.write(programmer.getId() + " : ");  // id
        writer.write(programmer.getName() + " : ");  // name
        writer.write(programmer.getPos() + " : ");  // Position
        writer.write(programmer.getColor().getColor() + " : ");
        writer.write(programmer.getLinguagens() + " : ");
        writer.write((programmer.isOutOfGame() ? "1" : "0") + " : ");  // out of game
        writer.write((programmer.isStuck() ? "1" : "0" + " : "));  // Stuck

        if (programmer.getCasasPercorridasList().size() > 3){
            writer.write(programmer.getCasasPercorridasList().get(programmer.getCasasPercorridasList().size() -3) +
                    " : " + programmer.getCasasPercorridasList().get(programmer.getCasasPercorridasList().size() -2)

            );
           // writer.write("\n");
        }
        if (programmer.getTools().size() != 0) {
            temTool = true;
            writer.write("\n");
        }
        for (Tool tool : programmer.getTools()) {
            writer.write(tool + " : ");
        }



    }

    private void writeGameHouseElements(FileWriter writer, GameHouseElement gameHouseElement) throws IOException {
        writer.write(gameHouseElement.getId() + " : ");  // id
        writer.write(gameHouseElement.getPos() + " : ");  // Position

    }

    public boolean saveGame(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            // Tamanho do Mapa
            writer.write(board.getTamanho() + "\n");
            //Current player
            writer.write(getCurrentPlayerID() + "\n");
            //Num de turnos
            writer.write(gameSetting.getEndedShifts() + "\n");
            // Programmers Size
            writer.write(getProgrammers(true).size() + "\n");

            for (Programmer p : getProgrammers(true)) {
                writePlayer(writer, p);
                writer.write("\n");
            }


            for (Tool tool : tools){
                writeGameHouseElements(writer, tool);
                writer.write("\n");
            }

            writer.write("ABYSS" + "\n");

            for (Abyss abyss : abysses.values()){
                writeGameHouseElements(writer, abyss);
                writer.write("\n");
            }

            writer.close();

        } catch (IOException e) {
            return false;
        }
        return true;

    }

    public boolean loadGame(File file) {

        try {
           // resetGame();
            Scanner fileReader = new Scanner(file);
            // Map Size
            String line = fileReader.nextLine();
            board.setTamanho(Integer.parseInt(line.trim()));
            // System.out.println(board.getTamanho());
            //CurrentPlayerID
            line = fileReader.nextLine();
            gameSetting.setCurrentPlayerID(Integer.parseInt(line.trim()));
            //System.out.println(getCurrentPlayerID());
            //Ended shifts
            line = fileReader.nextLine();
            gameSetting.setEndedShifts(Integer.parseInt(line.trim()));;
           // System.out.println(gameSetting.getEndedShifts());
            line = fileReader.nextLine();
            int programmersSize = Integer.parseInt(line.trim());


            int idAux = 0;

            for (int i = 0; i < programmersSize*2; i++) {
                line = fileReader.nextLine();
                String[] dados = line.split(":");
                int id = 0;
                List<Tool> tools = new ArrayList<>();
                if (dados[0].trim().equals("Herança") || dados[0].trim().equals("Programação Funcional")||
                        dados[0].trim().equals("Testes unitários") || dados[0].trim().equals("Tratamento de Excepções")
                        || dados[0].trim().equals("IDE") || dados[0].trim().equals("Ajuda Do Professor")){


                    for (String dado : dados) {
                        if (dado.equals("") || dado.equals(" ")){
                            break;
                        }
                        tools.add(Tool.associateToolToPlayer(AuxCode.setIdTool(dado.trim()), dado.trim()));

                    }


                    for (Programmer p : programmers.values()){
                       if (p.getId() == idAux){
                           p.setTools(tools);
                       }
                    }

                    continue;
                }
                id = Integer.parseInt(dados[0].trim());
                idAux = id;
                String name = dados[1].trim();
                int pos = Integer.parseInt(dados[2].trim());
                String color = dados[3].trim();

                ProgrammerColor cor = null;
                switch (color){
                    case "Purple":
                        cor = ProgrammerColor.PURPLE;
                        break;
                    case "Blue":
                        cor = ProgrammerColor.BLUE;
                        break;
                    case "Brown":
                        cor = ProgrammerColor.BROWN;
                        break;
                    case "Green":
                        cor = ProgrammerColor.GREEN;
                        break;

                    default:
                        break;
                }
                //List<String> linguagens = new ArrayList<>();
                String linguagens = dados[4];
                int outOfGame = Integer.parseInt(dados[5].trim());
                int stuck = Integer.parseInt(dados[6].trim());
                int duasCasasAnteriores;
                int casaAnterior;


                if (dados.length == 9){
                    duasCasasAnteriores = Integer.parseInt(dados[7].trim());
                    casaAnterior = Integer.parseInt(dados[8].trim());

                    programmers.put(id, new Programmer(id, name, pos, cor, linguagens, outOfGame, stuck));
                }
            }

            line = fileReader.nextLine();
            // line = fileReader.nextLine();
/*
            while (!line.equals("ABYSS")){
                String[] dados = line.split(":");
                int posMap = Integer.parseInt(dados[0].trim());
                int idTool = Integer.parseInt(dados[1].trim());




                Tool tool = Tool.createTool(idTool, AuxCode.setTitleTool(idTool), posMap);

                tools.add(tool);

                line = fileReader.nextLine();
            }*/



        } catch (IOException e) {
            return false;
        }


        return true;
    }


    public void resetGame() {

        programmers.clear();
        programmersOutOfGame.clear();
        programmerListGameResults.clear();
        totalProgrammers.clear();
        idProgrammers.clear();
        abysses.clear();
        tools.clear();
        gameSetting = new GameSetting();
        board = new Board();

    }

    public void createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools)
            throws InvalidInitialBoardException {


        //              ABYSSES & TOOLS

        createInitialBoard(playerInfo, worldSize);

        int idAbyss = 0;
        int idTool = 0;
        int i, j;
        int posAbyss = 0;
        int posTool = 0;

        for (i = 0; i < abyssesAndTools.length; i++) {

            if (abyssesAndTools[i] == null) {
                throw new InvalidInitialBoardException("O Abismo ou Ferramenta é NULL !");
            }


            // Var para detetar se é Abyss
            boolean abyssAlert = false;

            for (j = 0; j <= 2; j++) {

                if (abyssesAndTools[i][j] == null) {
                    throw new InvalidInitialBoardException("O Abismo ou Ferramenta é NULL !");
                }

                switch (j) {

                    // 0 -> Abismo  ||  1 -> Ferramenta
                    case 0:

                        // se nao for nenhum dos dois , false
                        if (!abyssesAndTools[i][0].equals(String.valueOf(0)) && !abyssesAndTools[i][0]
                                .equals(String.valueOf(1))) {

                            throw new InvalidInitialBoardException("Não é Abismo nem Ferramenta!");

                        }

                        // Se for 0 (abismo) o detetor da true
                        if (abyssesAndTools[i][0].equals(String.valueOf(0))) {
                            abyssAlert = true;
                        }
                        break;

                    case 1:
                        if (abyssesAndTools[i][1] == null) {
                            throw new InvalidInitialBoardException("O ID  é NULL !");
                        }
                        if (abyssAlert) {
                            if (Integer.parseInt(abyssesAndTools[i][1]) < 0 ||
                                    Integer.parseInt(abyssesAndTools[i][1]) > 9) {
                                throw new InvalidInitialBoardException("O ID é Inválido!");
                            }
                            idAbyss = Integer.parseInt(abyssesAndTools[i][1]);
                            break;
                        }

                        if (Integer.parseInt(abyssesAndTools[i][1]) < 0 ||
                                Integer.parseInt(abyssesAndTools[i][1]) > 5) {
                            throw new InvalidInitialBoardException("O ID é Inválido!");
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

                    Abyss abyss = Abyss.createAbyss(idAbyss, AuxCode.setTitleAbyss(idAbyss), posAbyss);
                    if (abyss == null) {
                        throw new InvalidInitialBoardException("O Abismo é NULL!");
                    }

                    abysses.put(idAbyss, abyss);
                    continue;
                }

                Tool tool = Tool.createTool(idTool, AuxCode.setTitleTool(idTool), posTool);
                if (tool == null) {
                    throw new InvalidInitialBoardException("A Ferramenta é NULL!");
                }

                tools.add(tool);

            }

        }


    }

    public void createInitialBoard(String[][] playerInfo, int worldSize) throws InvalidInitialBoardException {


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
            throw new InvalidInitialBoardException("O tamanho do mapa é inferior a 1!");
        }

        int i, j;


        for (i = 0; i < playerInfo.length; i++) {
            if (playerInfo[0] == null) {
                throw new InvalidInitialBoardException("O player é NULL!");
            }

            //Percorre as informaçoes de cada programador
            for (j = 0; j <= 3; j++) {

                //Se J for null E o num players inferior a 2, false
                if (playerInfo[i][j] == null && countPlayers < 2) {
                    throw new InvalidInitialBoardException("O player é NULL ou o numero de players é inferior a 2!");

                    //Se J for null E tiver o num players suficiente , true
                } else if (playerInfo[i][j] == null && countPlayers >= 2) {
                    return;
                }

                switch (j) {

                    // ID
                    case 0:

                        // Se o ID  for repetido OU  menor que 0 , false
                        if (idsRepetidos.contains(Integer.parseInt(playerInfo[i][j])) ||
                                (Integer.parseInt(playerInfo[i][j]) < 0)) {

                            throw new InvalidInitialBoardException("O ID é repetido OU  menor que 0");
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
                            throw new InvalidInitialBoardException("O ID é repetido OU  menor que 0");
                        }
                        nome = playerInfo[i][j];

                        break;

                    // LISTA LINGUAGENS DE PROGRAMACAO
                    case 2:
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0) {
                            throw new InvalidInitialBoardException("A lista de linguagens é NULL ou está vazia");
                        }

                        linguagensProgramacao = AuxCode.ordernarLinguagensProgramacao(playerInfo[i][j]);

                        break;

                    // COR
                    case 3:

                        //Se a cor for diferente das possiveis, false
                        if (!(playerInfo[i][j].equals("Purple") || playerInfo[i][j].equals("Green") ||
                                playerInfo[i][j].equals("Brown") || playerInfo[i][j].equals("Blue"))) {
                            throw new InvalidInitialBoardException("A cor nao é nenhuma das possiveis");

                        }
                        // Se existir uma repetida, false
                        if (coresRepetidas.contains(playerInfo[i][j])) {
                            throw new InvalidInitialBoardException("Existe uma cor repetida");
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
            throw new InvalidInitialBoardException("O numero de players não está entre 2 e 4");
        }

        // o tamanho do board tem que ser no minimo 2 peças por player .
        if (worldSize < (countPlayers * 2)) {
            throw new InvalidInitialBoardException("O tamanho do mapa tem que ser no minimo 2 casas por player");
        }


        // Ordeno o array de IDS para saber que está por ordem do mais pequeno para o maior
        Collections.sort(idProgrammers);

        //Declaro que o primeiro player a jogar é o que está em primeiro lugar , pois está ordenado do menor -> maior
        gameSetting.setCurrentPlayerID(idProgrammers.get(0));

        //Declaro o tamanho do mapa
        board.setTamanho(worldSize);

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
            if (AuxCode.isTool(tools, posAux)) {
                if (programmerTemp.catchTool(AuxCode.setTool(tools, posAux))) {
                    programmerTemp.catchTool(AuxCode.setTool(tools, posAux));
                    canCatch = true;
                }


                // (Se existir uma tool na casa   E   o player já a tenha na sua Lista de Tools )   OU   (Não exista
                // uma tool na casa) , ele nao faz absolutamente nada.
            }

            // Se existir um abismo na casa
            if (AuxCode.isAbyss(abysses, posAux)) {
                idAbyss = AuxCode.setIdAbyss(abysses, posAux);

                switch (idAbyss) {


                    // ERRO SINTAX -  Recua 1 casa
                    case 0:
                        idsCasas = 0;
                        steppedOnSyntaxError++;
                        break;


                    case 1:
                        idsCasas = 1;
                        gameSetting.setDiceShoot(nrSpaces);
                        steppedOnLogicError++;
                        break;


                    case 2:
                        idsCasas = 2;
                        steppedOnExceptionFault++;
                        break;


                    case 3:
                        idsCasas = 3;
                        steppedOnFileNotFoundException++;
                        break;


                    case 4:
                        idsCasas = 4;
                        steppedOnCrash++;
                        break;


                    // DUPLICATED CODE
                    // Recua até à casa onde estava antes de chegar a esta casa.
                    case 5:
                        idsCasas = 5;
                        steppedOnDuplicatedCode++;
                        break;


                    // EFEITOS SECUNDARIOS
                    //recua para a posição onde estava há 2 movimentos atrás
                    case 6:

                        idsCasas = 6;
                        steppedOnSideEffect++;
                        break;


                    //Blue Screen  -   perde imediatamente o jogo
                    case 7:
                        idsCasas = 7;
                        steppedOnBlueScreenDeath++;
                        break;


                    // CICLO INFINITO
                    case 8:
                        idsCasas = 8;
                        steppedOnInfiniteLoop++;
                        break;
                    // CORE DUMPED
                    case 9:
                        idsCasas = 9;
                        steppedOnSegmentationFault++;
                        break;

                    default:
                        break;

                }


            }

            AuxCode.changePosAndCasa(posAux, programmers, programmerTemp);

        }


        return true;
    }

    public String reactToAbyssOrTool() {


        boolean isTool = false;
        boolean isAbyss = false;
        Programmer programmerTemp = programmers.get(gameSetting.getCurrentPlayerID());

        // System.out.println(programmerTemp.getPos());

        //Por cada tool criada, se existir uma tool na pos do jogador
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

                                //ERRO SINTAX Recua 1 casa
                                case 0:
                                    int[] allowedTools0 = new int[2];
                                    allowedTools0[0] = 5;
                                    allowedTools0[1] = 4;
                                    reactAbyss(programmerTemp, allowedTools0, 1, 0);
                                    break;

                                //LOGICA - Recua metad3 do valor que tiver saido no dado
                                case 1:
                                    int[] allowedTools1 = new int[2];
                                    allowedTools1[0] = 5;
                                    allowedTools1[1] = 2;
                                    double auxSpaces = (double) gameSetting.getCurrentDiceShoot() / 2;
                                    reactAbyss(programmerTemp, allowedTools1, (int) Math.floor(auxSpaces), 0);
                                    break;

                                //EXCEPTION - recua 2 casas
                                case 2:
                                    int[] allowedTools2 = new int[2];
                                    allowedTools2[0] = 5;
                                    allowedTools2[1] = 3;
                                    reactAbyss(programmerTemp, allowedTools2, 2, 0);

                                    break;

                                // FILE NOT FOUND - recua 3 casas
                                case 3:
                                    int[] allowedTools3 = new int[2];
                                    allowedTools3[0] = 5;
                                    allowedTools3[1] = 3;
                                    reactAbyss(programmerTemp, allowedTools3, 3, 0);
                                    break;

                                //CRASH - volta à primeira casa
                                case 4:
                                    AuxCode.changePosAndCasa(1, programmers, programmerTemp);
                                    break;

                                // DUPLICATED CODE - Recuar ate à antiga casa onde o player estava
                                case 5:
                                    int[] allowedTools5 = new int[2];
                                    allowedTools5[0] = 0;
                                    reactAbyss(programmerTemp, allowedTools5, 0, 1);

                                    // EFEITOS SECUNDARIOS
                                case 6:
                                    int[] allowedTools6 = new int[2];
                                    allowedTools6[0] = 1;
                                    reactAbyss(programmerTemp, allowedTools6, 0, 2);
                                    break;

                                // BLUE SCREEN - perde imediatamente o jogo
                                case 7:

                                    int indexAuxToRemoveID = 0;
                                    for (Integer i : idProgrammers) {
                                        if (i == gameSetting.getCurrentPlayerID()) {
                                            break;
                                        }
                                        indexAuxToRemoveID++;
                                    }

                                    programmerListGameResults.add(programmerTemp);
                                    programmers.get(gameSetting.getCurrentPlayerID()).setOutOfGame();

                                    programmersOutOfGame.put(gameSetting.getCurrentPlayerID(),
                                            programmers.get(gameSetting.getCurrentPlayerID()));


                                    idProgrammers.remove(indexAuxToRemoveID);
                                    programmers.remove(gameSetting.getCurrentPlayerID());
                                    gameSetting.removeOneCount();

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

                                //Segmentation Fault
                                case 9:

                                    programmersInThisPositions = getProgrammers(programmerTemp.getPos());

                                    if (programmersInThisPositions.size() > 1) {
                                        for (Programmer p : programmersInThisPositions) {
                                            AuxCode.changePosAndCasa(p.getPos() - 3, programmers, p);
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

        // Incremento o count para ir buscar o proximo posicao no array IDS
        gameSetting.addOneCount();

        // Se o count chegar ao ids.size , comeca de novo para nao dar index out of bound
        if (gameSetting.getCount() >= idProgrammers.size()) {
            gameSetting.setCount(0);

        }

        //Declaro o proximo jogador a jogar
        gameSetting.setCurrentPlayerID(idProgrammers.get(gameSetting.getCount()));


        //Troco de turno incrementando os turnos terminados
        gameSetting.nextShift();

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

    public boolean reactAbyss(Programmer programmerTemp, int[] allowedTools, int penaltyCasas, int roolback) {
        boolean counterAbyss = false;

        //por cada tool que o programmer tem
        for (Tool tool : programmerTemp.getTools()) {
            //por cada tool que pode ajudar o programmer
            for (int allowedTool : allowedTools) {
                //se o programmer tiver alguma tool que o possa ajudar
                if (tool.getId() == allowedTool) {
                    counterAbyss = true;
                    programmerTemp.dropTool(tool);
                }
            }
            //se o programmer tiver alguma tool que o possa ajudar
            if (counterAbyss) {
                break;
            }

        }
        if (!counterAbyss && roolback == 0) {//se não for função de roolback
            AuxCode.changePosAndCasa(programmerTemp.getPos() - penaltyCasas,
                    programmers, programmerTemp);
        }

        if (!counterAbyss && roolback == 1) {//se for duplicated code
            AuxCode.changePosAndCasa(programmerTemp.getCasasPercorridasList().get(
                    programmerTemp.getCasasPercorridasList().size() - 2), programmers,
                    programmerTemp);
        }

        if (!counterAbyss && roolback == 2) {//se for efeitos secundarios
            AuxCode.changePosAndCasa(programmerTemp.getCasasPercorridasList().get(
                    programmerTemp.getCasasPercorridasList().size() - 3), programmers,
                    programmerTemp);
        }

        return counterAbyss;
    }


    public boolean gameIsOver() {


        // Se algum jogador chegar à ultima casa do mapa ,
        for (Programmer programmer : programmers.values()) {
            if (programmer.getPos() == board.getTamanho() || programmers.size() <= 1) {

                programmerListGameResults.addAll(programmers.values());

                //Declaro o Vencedor
                gameSetting.setWinner(programmer.getName());
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
            gameResults.add("" + gameSetting.getEndedShifts());
            gameResults.add("");
            gameResults.add("VENCEDOR");
            gameResults.add(gameSetting.getWinner());
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
                if (i.getName().equals(gameSetting.getWinner())) {
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

    public  HashMap<String, Integer> getSteppedOn(){
        HashMap<String, Integer> casas = new HashMap<>();
        casas.put("Syntax Error", steppedOnSyntaxError);
        casas.put("Logic Error", steppedOnLogicError);
        casas.put("Exception Fault", steppedOnExceptionFault);
        casas.put("FileNotFound Exception", steppedOnFileNotFoundException);
        casas.put("Crash", steppedOnCrash);
        casas.put("Duplicated Code", steppedOnDuplicatedCode);
        casas.put("Side Effect", steppedOnSideEffect);
        casas.put("BlueScreen Death", steppedOnBlueScreenDeath);
        casas.put("Infinite Loop", steppedOnInfiniteLoop);
        casas.put("Segmentation Fault", steppedOnSegmentationFault);

        return casas;
    }

}