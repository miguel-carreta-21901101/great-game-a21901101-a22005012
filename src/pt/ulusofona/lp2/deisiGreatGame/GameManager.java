package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class GameManager {

    // ID , CASA ATUAL
    HashMap<Integer, Integer> mapPositionPerPlayer = new HashMap<>();
    HashMap<Integer, Programmer> players = new HashMap<>();
    int count;
    int idMenor = Integer.MAX_VALUE;
    int jogadorAtual = 0;
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
                        id = Integer.parseInt(playerInfo[i][j]);;
                        if (id < idMenor){
                            idMenor = id;
                        }
                        break;

                    // NOME
                    case 1:

                        // Se o NOME nao for null OU vazios , false
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
                        ArrayList<String> linguagensProgramacao = null;
                        StringBuilder aux = new StringBuilder();

                        linguagensProgramacao = new ArrayList<>(Arrays.asList(playerInfo[i][j]));

                        for (String s : linguagensProgramacao){
                            aux.append(s).append(",");
                        }
                        linguagensProgramacaoAux = aux.substring(0, aux.length() -1);
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

            players.put(id, new Programmer(nome, id, linguagensProgramacaoAux, cor));
        }

        if (countPlayers <= 1 || countPlayers > 4){
            return false;
        }

        if (boardSize < (countPlayers * 2)){
            return false;
        }

        game.setCurrentPlayerID(idMenor);
        for (Programmer p : players.values()){
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

        for (Programmer p : players.values())
            if (p.getPos() == position){
                return "player" + p.getCor() + ".png";
            }

        if (position == board.getTamanho()) {
            return board.getUltimaPosicao();

        }

            return null;

    }

    public ArrayList<Programmer> getProgrammers() {

        //???
        ArrayList<Programmer> p = new ArrayList<>();
        Programmer p1 = new Programmer();
        Programmer p2 = new Programmer();
        p.add(p1);
        p.add(p2);

        return p;

        //return new ArrayList<Programmer>(players.values());
    }

    public ArrayList<Programmer> getProgrammers(int position) {

        ArrayList<Programmer> programmers = new ArrayList<>();

        if (position < 0 || position > board.getTamanho()){
            return null;
        }

        for (Programmer p : players.values()){
            if (p.getPos() == position){
                programmers.add(p);
            }
        }

        return programmers;
    }

    public int getCurrentPlayerID() {


        return 0;// game.getCurrentPlayerID();//this.game.getCurrentPlayerID();
    }


    public boolean moveCurrentPlayer(int nrPositions) {

        if (nrPositions < 1 || nrPositions > 6) {
            return false;
        }


        int aux;
        Programmer p = players.get(game.getCurrentPlayerID());


        if ((p.getPos() + nrPositions) > board.getTamanho()){
            aux = (board.getTamanho() - p.getPos() - nrPositions) * -1;
            p.setPos(board.getTamanho() - aux);

        } else {
           p.setPos(p.getPos() + nrPositions);
        }


        game.setCurrentPlayerID(game.getCurrentPlayerID()+1);
        count ++;
        game.nextShift();

        if (count == players.size()){
            count = 0;
            game.setCurrentPlayerID(idMenor);
        }

        return true;
    }

    public boolean gameIsOver() {
        for (Programmer p : players.values()){
            if (p.getPos() == board.getTamanho() ){
                return true;
            }
        }

        return false;

    }

    public ArrayList<String> getGameResults() {

        ArrayList<String> gameResults = new ArrayList<>();

        try {
            String classfic = "";

            String inicio = "O GRANDE JOGO DO DEISI\n\nNR. DE TURNOS\n" + game.getEndedShifts() + "\n\n" +
                    "VENCEDOR\n" + game.getWinner() + "\nRESTANTES";

            for (Integer i : game.getClassifications().keySet()) {
                if (i == 0) {
                    continue;
                }
                classfic = game.getClassifications().get(i).name + " " + i + "\n";
            }

            gameResults.add(inicio);
            gameResults.add(classfic);
        } catch (Exception e) {

            gameResults.clear();

        }


        return gameResults;
    }

    public JPanel getAuthorsPanel() {
        return null;
    }

}
