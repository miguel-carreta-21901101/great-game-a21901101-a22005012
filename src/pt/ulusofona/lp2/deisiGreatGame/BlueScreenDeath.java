package pt.ulusofona.lp2.deisiGreatGame;

public class BlueScreenDeath extends Abyss{
    public BlueScreenDeath(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String getTitleInfo(){
        return title;
    }


    @Override
    public String getImagePng(){
        return "bsod.png";
    }
}
