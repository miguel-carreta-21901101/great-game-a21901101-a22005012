package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.util.List;


public class GameManager {

    HashMap<Integer, Programmer> programmers = new HashMap<>();
    ArrayList<Integer> ids = new ArrayList<>();
    List<Programmer> programerList = new ArrayList<>();
    Board board = new Board();
    Game game = new Game();
    HashMap<Integer, Abyss> abysses = new HashMap<>();
    HashMap<Integer, Tool> tools = new HashMap<>();


    int count;

    public GameManager() {
    }

    public void resetGame(){
        programerList.clear();
        ids.clear();
        programmers.clear();
        abysses.clear();
        tools.clear();
        game = new Game();
        board = new Board();
        count = 0;
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {

        // Reset ao jogo
        resetGame();

        HashSet<Integer> idsRepetidos = new HashSet<>();
        HashSet<String> coresRepetidas = new HashSet<>();
        int countPlayers = 0;
        int idPlayers = 0;
        String nome = "";
        String linguagensProgramacao  = "";
        String cor = "";



        if (worldSize < 1) {
            return false;
        }

        int i, j;


        for (i = 0; i < playerInfo.length; i++) {
            if (playerInfo[0] == null){return false;}

            //Percorre as informaçoes de cada programador
            for (j = 0; j <= 3; j++) {

                //Se J for null E o num players inferior a 2, false
                if (playerInfo[i][j] == null && countPlayers < 2){
                    return false;

                //Se J for null E tiver o num players suficiente , true
                }else if (playerInfo[i][j] == null && countPlayers >= 2){
                    return true;
                }

                switch (j) {

                    // ID
                    case 0:

                        // Se o ID  for repetido OU  menor que 0 , false
                        if (idsRepetidos.contains(Integer.parseInt(playerInfo[i][j])) ||
                                (Integer.parseInt(playerInfo[i][j]) < 0) ) {

                            return false;
                        }
                        //adiciona o ID ao Hashset para validar se ha repetidos
                        idsRepetidos.add(Integer.parseInt(playerInfo[i][j]));
                        idPlayers = Integer.parseInt(playerInfo[i][j]);

                        //Adiciono ao array de ids para facilitar na escolha do CurrentPlayer mais à frente
                        ids.add(idPlayers);

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
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0){
                            return false;
                        }

                        linguagensProgramacao = AuxFunctions.ordernarLinguagensProgramacao(playerInfo[i][j]);

                        break;

                    // COR
                    case 3:

                        //Se a cor for diferente das possiveis, false
                        if (!(playerInfo[i][j].equals("Purple") || playerInfo[i][j].equals("Green") ||
                                playerInfo[i][j].equals("Brown") || playerInfo[i][j].equals("Blue"))){
                            return false;

                        }
                        // Se existir uma repetida, false
                        if (coresRepetidas.contains(playerInfo[i][j])){
                            return false;
                        }

                        //Adiciona a cor ao HashSet para validar as cores repetidas
                        coresRepetidas.add(playerInfo[i][j]);
                        cor =  playerInfo[i][j];
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
                    throw new IllegalArgumentException();
            }


        }

        // O num de players entre 2 - 4
        if (countPlayers <= 1 || countPlayers > 4){
            return false;
        }

        // o tamanho do board tem que ser no minimo 2 peças por player .
        if (worldSize < (countPlayers * 2)){
            return false;
        }

        //              ABYSSES & TOOLS


        int idAbyss = 0;
        int idTool = 0;

        int posAbyss = 0;
        int posTool = 0;

        for (i = 0; i < abyssesAndTools.length; i++){

            if (abyssesAndTools[i] == null) {return false;}


            boolean abyssAlert = false;

            for (j = 0; j <= 2; j++){

                if (abyssesAndTools[i][j] == null){
                    return false;
                }

                switch (j) {

                    case 0:
                        if (abyssesAndTools[i][0].equals(String.valueOf(0))){
                            abyssAlert = true;
                        }
                        break;

                    case 1:
                        if (abyssAlert){
                            idAbyss = Integer.parseInt(abyssesAndTools[i][1]);
                            break;
                        }

                        idTool = Integer.parseInt(abyssesAndTools[i][1]);
                        break;

                    case 2:
                        if (abyssAlert){
                            posAbyss = Integer.parseInt(abyssesAndTools[i][2]);
                            break;
                        }
                        posTool = Integer.parseInt(abyssesAndTools[i][2]);
                        break;
                }

                if (abyssAlert) {
                    abysses.put(idAbyss, new Abyss(idAbyss, AuxFunctions.setTitleAbyss(idAbyss), posAbyss));
                    continue;
                }

                tools.put(idTool, new Tool(idTool, AuxFunctions.setTitleTool(idTool), posTool));

            }


        }

        // Ordeno o array de IDS para saber que está por ordem do mais pequeno para o maior
        Collections.sort(ids);

        //Declaro que o primeiro player a jogar é o que está em primeiro lugar , pois está ordenado do menor -> maior
        game.setCurrentPlayerID(ids.get(0));

        //Declaro o tamanho do mapa
        board.setTamanho(worldSize);

        return true;

    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize){

        // Reset ao jogo
        resetGame();

        HashSet<Integer> idsRepetidos = new HashSet<>();
        HashSet<String> coresRepetidas = new HashSet<>();
        int countPlayers = 0;
        int id = 0;
        String nome = "";
        String linguagensProgramacao  = "";
        String cor = "";

        if (worldSize < 1) {
            return false;
        }

        int i, j;


        for (i = 0; i < playerInfo.length; i++) {
            if (playerInfo[0] == null){return false;}

            //Percorre as informaçoes de cada programador
            for (j = 0; j <= 3; j++) {

                //Se J for null E o num players inferior a 2, false
                if (playerInfo[i][j] == null && countPlayers < 2){
                    return false;

                    //Se J for null E tiver o num players suficiente , true
                }else if (playerInfo[i][j] == null && countPlayers >= 2){
                    return true;
                }

                switch (j) {

                    // ID
                    case 0:

                        // Se o ID  for repetido OU  menor que 0 , false
                        if (idsRepetidos.contains(Integer.parseInt(playerInfo[i][j])) ||
                                (Integer.parseInt(playerInfo[i][j]) < 0) ) {

                            return false;
                        }
                        //adiciona o ID ao Hashset para validar se ha repetidos
                        idsRepetidos.add(Integer.parseInt(playerInfo[i][j]));
                        id = Integer.parseInt(playerInfo[i][j]);

                        //Adiciono ao array de ids para facilitar na escolha do CurrentPlayer mais à frente
                        ids.add(id);

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
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0){
                            return false;
                        }

                        linguagensProgramacao = AuxFunctions.ordernarLinguagensProgramacao(playerInfo[i][j]);

                        break;

                    // COR
                    case 3:

                        //Se a cor for diferente das possiveis, false
                        if (!(playerInfo[i][j].equals("Purple") || playerInfo[i][j].equals("Green") ||
                                playerInfo[i][j].equals("Brown") || playerInfo[i][j].equals("Blue"))){
                            return false;

                        }
                        // Se existir uma repetida, false
                        if (coresRepetidas.contains(playerInfo[i][j])){
                            return false;
                        }

                        //Adiciona a cor ao HashSet para validar as cores repetidas
                        coresRepetidas.add(playerInfo[i][j]);
                        cor =  playerInfo[i][j];
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
                    throw new IllegalArgumentException();
            }


        }

        // O num de players entre 2 - 4
        if (countPlayers <= 1 || countPlayers > 4){
            return false;
        }

        // o tamanho do board tem que ser no minimo 2 peças por player .
        if (worldSize < (countPlayers * 2)){
            return false;
        }


        // Ordeno o array de IDS para saber que está por ordem do mais pequeno para o maior
        Collections.sort(ids);

        //Declaro que o primeiro player a jogar é o que está em primeiro lugar , pois está ordenado do menor -> maior
        game.setCurrentPlayerID(ids.get(0));

        //Declaro o tamanho do mapa
        board.setTamanho(worldSize);

        return true;

    }


    public String getImagePng(int position) {

        // position = pos no mapa

        if (position > board.getTamanho() || position < 1) {
            return null;
        }

        if (position == board.getTamanho()) {
            return board.getUltimaPosicaoDoMapa();

        }

        //Se o programmer estiver na posicao POSITION , retorna a imagem do player
        for (Programmer p : programmers.values()) {
            if (p.getPos() == position) {
                return "player" + p.getColor() + ".png";
            }
        }

        // Se houver um abismo na posicao POSITION , retorna a img que pertence
        for ( Abyss abyss : abysses.values()){
            if (abyss.getPos() == position){

                switch(abyss.getId()){

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
        for (Tool tool: tools.values()){
            if (tool.getPos() == position){

                switch(tool.getId()){

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

        return null;

    }


    public String getTitle(int position){

        if (position > board.getTamanho() || position < 1){
            return null;
        }

        for (Abyss a: abysses.values()){
            if (a.getPos() == position){
               return a.getTitulo();
            }
        }


        for (Tool t : tools.values()){
            if (t.getPos() == position){
                return t.getTitulo();
            }
        }

        return null;
    }



    //TODO
    public List<Programmer> getProgrammers(boolean includeDefeated) {

        //Devolve os values do hashmap programmers
        if (includeDefeated) {
            return new ArrayList<>(programmers.values());
        }
        List<Programmer> noDefeatedProgrammers = new ArrayList<>();


        for (Programmer p : programmers.values()){
            if (!p.isOutOfGame()){
                noDefeatedProgrammers.add(p);
            }
        }
        return noDefeatedProgrammers;

    }

    public List<Programmer> getProgrammers(int position) {

        ArrayList<Programmer> programmers = new ArrayList<>();

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


    public String getProgrammersInfo(){

        StringBuilder info = new StringBuilder();

        for (Integer i : ids){
            Programmer p =  programmers.get(i);

            info.append(p.getName()).append(" : ");

            if (p.getTools().size() == 0){
                info.append("No tools | ");
            } else {

                StringBuilder auxBuilder = new StringBuilder();

                for (Tool t : p.getTools()){
                    auxBuilder.append(t.getTitulo());
                    auxBuilder.append(";");
                }

                info.append(auxBuilder.substring(0, auxBuilder.length() -1));

                info.append(" | ");
            }


        }
        return info.substring(0, info.length() -3);
    }

    public int getCurrentPlayerID() {

        return game.getCurrentPlayerID();
    }

    //****************************************************************************************
    // FUNCOES PARA MUDAR DE SITIO TALVEZ

    public Tool setTool(int pos) {
        for (Tool toolTemp : tools.values()) {
            if (toolTemp.getPos() == pos) {
                return toolTemp;
            }
        }

        return null;
    }
    public boolean temTool(int pos){
        for (Tool toolTemp : tools.values()){
            if (toolTemp.getPos() == pos) {

                return true;
            }
        }
        return false;
    }
    public boolean temAbismo(int pos){
        for (Abyss abyss : abysses.values()){
            if (abyss.getPos() == pos){
                return true;
            }
        }
        return false;
    }
    public int setIdAbyss (int pos){
        for (Abyss abyss : abysses.values()){
            if (abyss.getPos() == pos){
                return abyss.getId();
            }
        }
        return -1;
    }
    public void changePosAndCasa(int pos, HashMap<Integer, Programmer> programmers, Programmer programmer){
        programmers.get(programmer.getId()).setPos(pos);
        programmers.get(programmer.getId()).adicionaCasa(game.getEndedShifts(), pos);
    }
    //****************************************************************************************


    public boolean moveCurrentPlayer(int nrSpaces){

        // numeros do dado 1 - 6
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        int aux;


        // Replico o programmer que esta neste momento a jogar
        Programmer programmerTemp = programmers.get(game.getCurrentPlayerID());
        Tool tool;
        int idAbyss;


        // Se o programmer ultrapassar a casa final do jogo :
        if ((programmerTemp.getPos() + nrSpaces) > board.getTamanho()){


            // Recua o numero de casas em excesso
            aux = (board.getTamanho() - programmerTemp.getPos() - nrSpaces) * -1;


            //Adiciono ao verdadeiro programmer a sua posicao atual
            programmers.get(programmerTemp.getId()).setPos(board.getTamanho() - aux);

        } else {

            int posAux = programmerTemp.getPos() + nrSpaces;
            int novaPos;

            if (temTool(posAux)){
                tool = setTool(posAux);
                programmerTemp.catchTool(tool);
            }

            if (temAbismo(posAux)){
                idAbyss = setIdAbyss(posAux);

                switch (idAbyss){
                    case 0:
                        novaPos = posAux -1;
                        changePosAndCasa(novaPos, programmers, programmerTemp);
                        //programmers.get(programmerTemp.getId()).setPos(novaPos);
                        //programmers.get(programmerTemp.getId()).adicionaCasa(game.getEndedShifts(), novaPos);
                        break;

                    case 1:

                        double auxSpaces = (double) nrSpaces/2;
                        novaPos = posAux - (int)Math.floor(auxSpaces);
                        changePosAndCasa(novaPos, programmers, programmerTemp);
                      //  programmers.get(programmerTemp.getId()).setPos(novaPos);
                       // programmers.get(programmerTemp.getId()).adicionaCasa(game.getEndedShifts(), novaPos);
                        break;

                    case 2:
                        novaPos = posAux -2;
                        changePosAndCasa(novaPos, programmers, programmerTemp);
                        //programmers.get(programmerTemp.getId()).setPos(novaPos);
                        //programmers.get(programmerTemp.getId()).adicionaCasa(game.getEndedShifts(), novaPos);
                        break;

                    case 3:
                        novaPos = posAux - 3;
                        changePosAndCasa(novaPos, programmers, programmerTemp);
                        //programmers.get(programmerTemp.getId()).setPos(novaPos);
                        //programmers.get(programmerTemp.getId()).adicionaCasa(game.getEndedShifts(), novaPos);
                        break;

                    case 4:
                        novaPos = 1;
                        changePosAndCasa(novaPos, programmers, programmerTemp);
                      //  programmers.get(programmerTemp.getId()).setPos(novaPos);
                       // programmers.get(programmerTemp.getId()).adicionaCasa(game.getEndedShifts(), novaPos);
                        break;

                    case 5:

                        novaPos = programmers.get(programmerTemp.getId()).getCasasPercorridas()
                                .get(game.getEndedShifts() -1);

                        changePosAndCasa(novaPos, programmers, programmerTemp);
                       // programmers.get(programmerTemp.getId()).setPos(novaPos);
                       // programmers.get(programmerTemp.getId()).adicionaCasa(game.getEndedShifts(), novaPos);
                        break;

                    case 6:
                        novaPos = programmers.get(programmerTemp.getId()).getCasasPercorridas()
                                .get(game.getEndedShifts() -2);
                        changePosAndCasa(novaPos, programmers, programmerTemp);
                      //  programmers.get(programmerTemp.getId()).setPos(novaPos);
                       // programmers.get(programmerTemp.getId()).adicionaCasa(game.getEndedShifts(), novaPos);
                        break;

                    case 7:
                        programmers.get(programmerTemp.getId()).setOutOfGame();

                    case 8:
                    case 9:

                }
               // changePosAndCasa(novaPos, programmers, programmerTemp);
                programmers.get(programmerTemp.getId()).gotAbyssLastRound();
            }
            // Adiciono ao verdadeiro programmer a sua posicao atual
            programmers.get(programmerTemp.getId()).setPos(programmerTemp.getPos() + nrSpaces);

        }




        return true;
    }

    public String reactToAbyssOrTool(){

        boolean isTool = false;
        boolean isAbyss = false;

        for (Tool tool : tools.values()){
            if (programmers.get(game.getCurrentPlayerID()).getPos() == tool.getPos()){
                isTool = true;
            }
        }

        if (!isTool){
            for (Abyss abyss : abysses.values()){
                if (programmers.get(game.getCurrentPlayerID()).getPos() == abyss.getPos()){
                    isAbyss = true;
                }
            }
        }

        // Incremento o count para ir buscar o proximo posicao no array IDS
        count ++;

        // Se o count chegar ao ids.size , comeca de novo para nao dar index out of bound
        if (count == ids.size()){
            count = 0;
        }

        //Declaro o proximo jogador a jogar
        game.setCurrentPlayerID(ids.get(count));


        //Troco de turno incrementando os turnos terminados
        game.nextShift();

        if (isTool){
            return "Parabéns, apanhou uma ferramenta !!";

        } else if (isAbyss){
            return "Ohhh, caiu num abismo !!";
        } else {
            return null;
        }


    }

    public boolean gameIsOver() {

        // Se algum jogador chegar à ultima casa do mapa ,
        for (Programmer p : programmers.values()){
            if (p.getPos() == board.getTamanho()){

                programerList.addAll(programmers.values());

                //Declaro o Vencedor
                game.setWinner(p.getName());
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

            programerList.sort((p1, p2) -> Integer.compare(p2.getPos(), p1.getPos()));


            for (Programmer i : programerList){
                //se o nome do programer é o winner, passo à frente
                if ( i.getName().equals(game.getWinner())){
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

        painel.setPreferredSize(new Dimension(300,300));
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
