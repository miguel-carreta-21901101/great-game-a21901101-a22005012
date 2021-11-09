package pt.ulusofona.lp2.deisiGreatGame;

import javafx.collections.transformation.SortedList;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class GameManager {

    int count;
    int idMenor = Integer.MAX_VALUE;
    HashMap<Integer, Programmer> programmers = new HashMap<>();

    HashMap<String, Integer> classificados = new HashMap<>();
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
        ArrayList<Integer> idsAJogar = new ArrayList<>();


        String linguagensProgramacaoAux  = "";
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


                        // Se o ID nao for repetido OU  menor que 0 , false
                        if (idsRepetidos.contains(Integer.parseInt(playerInfo[i][j])) ||
                                (Integer.parseInt(playerInfo[i][j]) < 0) ) {

                            return false;
                        }
                        //adiciona o ID ao Hashset
                        idsRepetidos.add(Integer.parseInt(playerInfo[i][j]));
                        id = Integer.parseInt(playerInfo[i][j]);
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
                        ArrayList<String> linguagensProgramacao;
                        StringBuilder aux = new StringBuilder();

                        linguagensProgramacao = new ArrayList<>(Arrays.asList(playerInfo[i][j]));


                        for (String s : linguagensProgramacao){
                            aux.append(s).append("; ");
                        }
                        linguagensProgramacaoAux = aux.substring(0, aux.length() -2);
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


            programmers.put(id, new Programmer(id, nome, linguagensProgramacaoAux, cor));
        }

        if (countPlayers <= 1 || countPlayers > 4){
            return false;
        }

        if (boardSize < (countPlayers * 2)){
            return false;
        }

        game.setCurrentPlayerID(idMenor);

        for (Programmer p : programmers.values()){
            if (p.getId() == idMenor){
                continue;
            }
            idsAJogar.add(p.getId());
        }
        board.setTamanho(boardSize);



        return true;

    }

    public String getImagePng(int position) {


        if (position > board.getTamanho() || position < 1) {
            return null;
        }

        for (Programmer p : programmers.values()) {
            if (p.getPos() == position) {
                return "player" + p.getCor() + ".png";
            }
        }
        if (position == board.getTamanho()) {
            return board.getUltimaPosicao();

        }else if (position <= board.getTamanho()){
            return "blank.png";

        }else {
            return null;
        }




    }

    public ArrayList<Programmer> getProgrammers() {

        return new ArrayList<>(programmers.values());
    }

    public ArrayList<Programmer> getProgrammers(int position) {

        ArrayList<Programmer> programmers = new ArrayList<>();
        boolean existemProgrammers = false;

        if (position < 0 || position > board.getTamanho()){
            return null;
        }

        for (Programmer p : this.programmers.values()){
            if (p.getPos() == position){
                existemProgrammers = true;
                programmers.add(p);
            }
        }

        if (existemProgrammers){
            return programmers;
        }else {
            return null;
        }
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
                    classificados.put(p1.getName(), p1.getPos());
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
            gameResults.add("Vencedor");
            gameResults.add(game.getWinner());
            gameResults.add("");
            gameResults.add("RESTANTES");


            for (String s : classificados.keySet()){
                if (s.equals(game.getWinner())){
                    continue;
                }
                /*HashMap<String, Integer> aux = new HashMap<>();
                for (Integer i : classificados.values()){

                }*/
                gameResults.add(s + " " + classificados.get(s));
            }


            /*
            String inicio = "O GRANDE JOGO DO DEISI\n\nNR. DE TURNOS\n" + game.getEndedShifts() + "\n\n" +
                    "VENCEDOR\n" + game.getWinner() + "\nRESTANTES";

            for (Integer i : game.getClassifications().keySet()) {
                if (i == 0) {
                    continue;
                }
                classfic = game.getClassifications().get(i).name + " " + i + "\n";
            }

            gameResults.add(inicio);
            gameResults.add(classfic);*/
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
