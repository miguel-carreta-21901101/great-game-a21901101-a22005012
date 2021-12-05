package pt.ulusofona.lp2.deisiGreatGame;

public class InfiniteLoop extends Abyss{
    public InfiniteLoop(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String getTitleInfo(){
        return title;
    }

    @Override
    public String getImagePng(){
        return "infinite-loop.png";
    }
}
