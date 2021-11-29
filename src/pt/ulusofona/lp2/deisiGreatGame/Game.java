package pt.ulusofona.lp2.deisiGreatGame;

public class Game {

    private int currentPlayerID = 0;
    private int endedShifts = 1;
    private String winner;
    private int currentDiceShoot;
    private int count;
    public Game() {
    }

    // GETTERS

    public int getCurrentPlayerID(){


        return currentPlayerID;
    }

    public String getWinner() {
        return winner;
    }

    public int getEndedShifts() {
        return endedShifts;
    }

    public int getCurrentDiceShoot(){return currentDiceShoot;}

    public int getCount() {
        return count;
    }
//SETTERS

    public void setCount(int count) {
        this.count = count;
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

    public void setDiceShoot(int shoot){this.currentDiceShoot = shoot;}

    //FUNS

    public void addOneCount(){
        count += 1;
    }
    public void removeOneCount(){count -= 1;}
    public void nextShift() {
        endedShifts ++;
       // currentPlayerID++;
    }

}
