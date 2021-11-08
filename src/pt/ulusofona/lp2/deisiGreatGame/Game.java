package pt.ulusofona.lp2.deisiGreatGame;

import java.util.HashMap;

public class Game {

    int currentPlayerID, casaPartida = 0;
    int endedShifts = 1;
    private String winner;

    private HashMap<Integer, Programmer> classifications = new HashMap<>();

    public Game() {
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void nextShift() {
        endedShifts ++;
    }


    public HashMap<Integer, Programmer> getClassifications() {
        return classifications;
    }

    public int getCurrentPlayerID(){

        return currentPlayerID;
    }

    public String getWinner() {
        return winner;
    }

    public int getEndedShifts() {
        return endedShifts;
    }


}
