package pt.ulusofona.lp2.deisiGreatGame;

public class CatchException extends Tool{
    public CatchException(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String toString(){
        return title;
    }
    @Override
    public String getImagePng(){
        return "catch.png";
    }
}
