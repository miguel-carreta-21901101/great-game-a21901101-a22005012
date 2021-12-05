package pt.ulusofona.lp2.deisiGreatGame;

public class LogicError extends Abyss{
    public LogicError(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String getTitleInfo(){
        return title;
    }

    @Override
    public String getImagePng(){
        return "logic.png";
    }
}
