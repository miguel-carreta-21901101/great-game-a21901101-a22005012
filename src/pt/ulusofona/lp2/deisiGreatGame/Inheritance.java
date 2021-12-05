package pt.ulusofona.lp2.deisiGreatGame;

public class Inheritance extends Tool{
    public Inheritance(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String getTitleInfo(){
        return title;
    }
    @Override
    public String getImagePng(){
        return "inheritance.png";
    }
}
