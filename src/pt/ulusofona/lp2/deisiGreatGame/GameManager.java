package pt.ulusofona.lp2.deisiGreatGame;

import javafx.scene.control.TableColumn;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private int currentPlayerID, endedShifts = 0;
    private String winner;
    private int[] ids = new int[3];

    public GameManager() {
    }

    HashMap<Integer, Integer> mapPositionPerPlayer = new HashMap<>();
    HashMap<Integer, Programmer> players = new HashMap<>();
    HashMap<Integer, Programmer> classifications = new HashMap<>();

    Tabuleiro tabuleiro = new Tabuleiro();

    public boolean createInitialBoard(String[][] playerInfo, int boardSize){

        //  Qual o tamanho minimo ??

        if (boardSize < 1  ){
            return false;
        }

        return false;

    }


    public String getImagePng(int position){

        if (position > tabuleiro.getTamanho() || position < 1){
            return null;
        }

        return tabuleiro.ultimaPosicao;

    }

    public ArrayList<Programmer> getProgrammers(){

        ArrayList<Programmer> programmers = new ArrayList<>();

        for (Integer i : players.keySet()){
            programmers.add(players.get(i));
        }

        return programmers;

    }


    public ArrayList<Programmer> getProgrammers(int position){

        /*for(Programmer p : Tabuleiro.posicoesMapa.get(position)){
            playersInPosition.add(p);
        }
        return new ArrayList<>(Tabuleiro.posicoesMapa.get(position));*/

        return Programmer.programmers;
    }

    public int getCurrentPlayerID(){
        return currentPlayerID;
    }

    public boolean moveCurrentPlayer(int nrPositions){

        if (nrPositions < 1 || nrPositions > 6){
            return false;
        }

        int aux;

        if ((mapPositionPerPlayer.get(getCurrentPlayerID()) + nrPositions) > tabuleiro.tamanho){
            aux = tabuleiro.tamanho - mapPositionPerPlayer.get(getCurrentPlayerID()) -nrPositions;

            mapPositionPerPlayer.put(getCurrentPlayerID(), aux);
        }

        mapPositionPerPlayer.put(getCurrentPlayerID(), nrPositions);

        return true;
    }

    public boolean gameIsOver(){
        for (Integer i:mapPositionPerPlayer.values()){
            if (i == tabuleiro.tamanho){
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> getGameResults() {

        ArrayList<String> gameResults = new ArrayList<>();

        try{
            String classfic = "";

            String inicio = "O GRANDE JOGO DO DEISI\n\nNR. DE TURNOS\n" + endedShifts +"\n\n" +
                    "VENCEDOR\n" + winner +"\nRESTANTES";

            for (Integer i : classifications.keySet()){
                if (i == 0){
                    continue;
                }
                classfic = classifications.get(i).name + " " + i + "\n";
            }

            gameResults.add(inicio);
            gameResults.add(classfic);
        }catch (Exception e){

            gameResults.clear();

        }


        return gameResults;
    }


    public JPanel getAuthorsPanel(){
        return null;
    }

}
