package pt.ulusofona.lp2.deisiGreatGame;

public class ExceptionFault extends Abyss{
    public ExceptionFault(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String toString(){
        return title;
    }

    @Override
    public String getImagePng(){
        return "exception.png";
    }
}