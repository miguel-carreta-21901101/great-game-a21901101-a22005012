package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception {
    String message;


    public InvalidInitialBoardException() {
    }

    public InvalidInitialBoardException(String message) {
        this.message = message;
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
        return "";
    }
}
