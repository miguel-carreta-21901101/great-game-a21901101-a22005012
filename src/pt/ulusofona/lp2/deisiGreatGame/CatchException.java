package pt.ulusofona.lp2.deisiGreatGame;

public class CatchException extends Tool{
    public CatchException(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }


    public CatchException(int id, String title){
        super(id, title);

    }

    @Override
    public String getImagePng(){
        return "catch.png";
    }
}
