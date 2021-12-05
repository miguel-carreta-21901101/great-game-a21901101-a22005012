package pt.ulusofona.lp2.deisiGreatGame;

public class FunctionalProgramming extends Tool{
    public FunctionalProgramming(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String getTitleInfo(){
        return title;
    }
    @Override
    public String getImagePng(){
        return "functional.png";
    }
}
