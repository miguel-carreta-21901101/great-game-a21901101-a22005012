package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception {
    String message;
    boolean isInvalid;
    int i;

    public InvalidInitialBoardException() {
    }

    public InvalidInitialBoardException(String message) {
        this.message = message;
    }
    public InvalidInitialBoardException(String message, int i, int j){

    }
    public InvalidInitialBoardException(int i){
        this.i = i;
    }

    public String getMessage() {
        return message;
    }

    public boolean isInvalidAbyss(){
        return true;
    }

    public boolean isInvalidTool(){
        return true;
    }

    public String getTypeId(){
        return Integer.toString(i);
    }
}
