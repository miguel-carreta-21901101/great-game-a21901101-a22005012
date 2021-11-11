package pt.ulusofona.lp2.deisiGreatGame;

public class Game {

    private int currentPlayerID= 0;
    private int endedShifts = 1;
    private String winner;

    public Game() {
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
    }

    public void setEndedShifts(int shifts){
        this.endedShifts = shifts;
    }

    public void setWinner(String winner) {
        this.winner = winner;
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

    public void nextShift() {
        endedShifts ++;
        currentPlayerID++;
    }

}
