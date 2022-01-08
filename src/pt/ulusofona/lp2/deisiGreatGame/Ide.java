package pt.ulusofona.lp2.deisiGreatGame;

public class Ide extends Tool{
    public Ide(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }

    public Ide(int id, String title){
        super(id, title);

    }


    @Override
    public String getImagePng(){
        return "IDE.png";
    }
}