package pt.ulusofona.lp2.deisiGreatGame;

public class SideEffect extends Abyss{
    public SideEffect(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }


    @Override
    public String getImagePng(){
        return "secondary-effects.png";
    }
}
