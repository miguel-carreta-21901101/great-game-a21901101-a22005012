package pt.ulusofona.lp2.deisiGreatGame;

public class Inheritance extends Tool{
    public Inheritance(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }

    public Inheritance(int id, String title){
        super(id, title);

    }

    @Override
    public String getImagePng(){
        return "inheritance.png";
    }
}
