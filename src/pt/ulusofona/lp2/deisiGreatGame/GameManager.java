package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class GameManager {

    HashMap<Integer, Integer> mapPositionPerPlayer = new HashMap<>();

    Programmer programmer = new Programmer();
    Board board = new Board();
    Game game = new Game();


    public GameManager() {
    }


    public boolean createInitialBoard(String[][] playerInfo, int boardSize) {

        HashSet<Integer> idsRepetidos = new HashSet<>();
        HashSet<String> coresRepetidas = new HashSet<>();
        int countPlayers = 0;

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

               // System.out.println(playerInfo[i][j]);
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
                        break;

                    // NOME
                    case 1:

                        // Se o NOME nao for null OU vazios , false
                        if (playerInfo[i][j] == null || playerInfo[i][j].length() == 0) {
                            return false;
                        }
                        break;

                    // LISTA LINGUAGENS DE PROGRAMACAO
                    case 2:
                        if (playerInfo[i][2] == null || playerInfo[i][2].length() == 0){
                            return false;
                        }
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

                        break;

                }
            }
            countPlayers++;
        }

        if (countPlayers <= 1 || countPlayers > 4){
            return false;
        }

        if (boardSize < (countPlayers * 2)){
            return false;
        }

        board.setTamanho(boardSize);

        return true;

    }

    public String getImagePng(int position) {

        if (position > board.getTamanho() || position < 1) {
            return null;
        }

        if (position == board.getTamanho()){
            return board.getUltimaPosicao();
        }
        return "";

    }

    public ArrayList<Programmer> getProgrammers() {
        ArrayList<Programmer> programmers = new ArrayList<>();

        Programmer p1 = new Programmer();
        Programmer p2 = new Programmer();

        programmers.add(p1);
        programmers.add(p2);
        //return new ArrayList<>(game.getPlayers().values());}
        return programmers;
    }

    public ArrayList<Programmer> getProgrammers(int position) {


        if (position < 0 || position > board.getTamanho()){
            return null;
        }

        return board.getPosicoesMapa().get(position);


    }

    public int getCurrentPlayerID() {
        return game.getCurrentPlayerID();
    }

    public boolean moveCurrentPlayer(int nrPositions) {

        if (nrPositions < 1 || nrPositions > 6) {
            return false;
        }

        int auxCasaJogo;

        if ((mapPositionPerPlayer.get(getCurrentPlayerID()) + nrPositions) > board.getTamanho()) {
            auxCasaJogo = board.getTamanho() - mapPositionPerPlayer.get(getCurrentPlayerID()) - nrPositions;

            mapPositionPerPlayer.put(getCurrentPlayerID(), auxCasaJogo);
        } else {

            mapPositionPerPlayer.put(getCurrentPlayerID(), mapPositionPerPlayer.get(getCurrentPlayerID() +
                    nrPositions));
        }
        return true;
    }

    public boolean gameIsOver() {
        return mapPositionPerPlayer.containsValue(board.getTamanho());
       /* for (Integer i : mapPositionPerPlayer.values()) {
            if (i == board.getTamanho()) {
                return true;
            }
        }*/
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
