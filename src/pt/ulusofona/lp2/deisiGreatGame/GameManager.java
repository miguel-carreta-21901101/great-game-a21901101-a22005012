package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;

import java.awt.*;
import java.util.*;


public class GameManager {

    int count;
    int idMenor = Integer.MAX_VALUE;
    HashMap<Integer, Programmer> programmers = new HashMap<>();
    TreeMap<Integer, String> classF = new TreeMap<>(Collections.reverseOrder());

    Board board = new Board();
    Game game = new Game();


    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int boardSize) {

        HashSet<Integer> idsRepetidos = new HashSet<>();
        HashSet<String> coresRepetidas = new HashSet<>();
        int countPlayers = 0;
        int id = 0;
        String nome = "";
        String linguagensProgramacao  = "";
        String cor = "";

        if (boardSize < 1) {
            return false;
        }

        int i, j;


        for (i = 0; i < playerInfo.length; i++) {
            if (playerInfo[0] == null){

                return false;
            }

            //Percorre as informaçoes de cada programador
            for (j = 0; j <= 3; j++) {

                if (playerInfo[i][j] == null && countPlayers < 2){

                    return false;
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
                        //adiciona o ID ao Hashset
                        idsRepetidos.add(Integer.parseInt(playerInfo[i][j]));
                        id = Integer.parseInt(playerInfo[i][j]);

                        //Procura o ID mais pequeno
                        if (id < idMenor){
                            idMenor = id;
                        }
                        break;

                    // NOME
                    case 1:

                        // Se o NOME for null OU vazios , false
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0) {
                            return false;
                        }
                        nome = playerInfo[i][j];

                        break;

                    // LISTA LINGUAGENS DE PROGRAMACAO
                    case 2:
                        if (playerInfo[i][2] == null || playerInfo[i][2].length() == 0){
                            return false;
                        }

                        String[] parts = playerInfo[i][j].split(";");
                        StringBuilder aux = new StringBuilder();

                        Arrays.sort(parts);

                        for (String s: parts){
                            aux.append(s);
                            aux.append("; ");
                        }

                        linguagensProgramacao = aux.substring(0, aux.length() -2);

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
        if (boardSize < (countPlayers * 2)){
            return false;
        }

        // Declaro que o Primeiro a jogar é quem tem o ID mais pequeno
        game.setCurrentPlayerID(idMenor);

        //Declaro o tamanho do mapa
        board.setTamanho(boardSize);

        return true;

    }

    public String getImagePng(int position) {

        // position = pos no mapa

        if (position > board.getTamanho() || position < 1) {
            return null;
        }

        //Se o programmer estiver na posicao POSITION , retorna a imagem do player
        for (Programmer p : programmers.values()) {
            if (p.getPos() == position) {
                return "player" + p.getColor() + ".png";
            }
        }
        // Se a POSITION for a ultima posicao do mapa
        if (position == board.getTamanho()) {
            return board.getUltimaPosicaoDoMapa();

        }else {
            return null;
        }

    }

    public ArrayList<Programmer> getProgrammers() {

        //Devolve os values do hashmap programmers
        return new ArrayList<>(programmers.values());
    }

    public ArrayList<Programmer> getProgrammers(int position) {

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

    public int getCurrentPlayerID() {

        return game.getCurrentPlayerID();
    }

    public boolean moveCurrentPlayer(int nrPositions) {

        // numeros do dado 1 - 6
        if (nrPositions < 1 || nrPositions > 6) {
            return false;
        }

        int aux;
        Programmer p = programmers.get(game.getCurrentPlayerID());


        if ((p.getPos() + nrPositions) > board.getTamanho()){

            aux = (board.getTamanho() - p.getPos() - nrPositions) * -1;

            programmers.get(p.getId()).setPos(board.getTamanho() - aux);

        } else {
            programmers.get(p.getId()).setPos(p.getPos() + nrPositions);

        }


        game.setCurrentPlayerID(game.getCurrentPlayerID());

        count ++;
        game.nextShift();

        if (count == programmers.size()){
            count = 0;
            game.setCurrentPlayerID(idMenor);
        }

        return true;
    }

    public boolean gameIsOver() {
        boolean isOver = false;

        for (Programmer p : programmers.values()){
            if (p.getPos() == board.getTamanho()){
                for (Programmer p1: programmers.values()){
                    classF.put(p1.getPos(), p1.getName());

                }
                game.setWinner(p.getName());
                isOver = true;
                break;

            }
        }
        return isOver;

    }

    public ArrayList<String> getGameResults() {

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


            for (Integer i : classF.keySet()){
                if (classF.get(i).equals(game.getWinner())){
                    continue;
                }

                gameResults.add(classF.get(i) + " " + i);
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
