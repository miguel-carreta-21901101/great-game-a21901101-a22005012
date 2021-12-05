package pt.ulusofona.lp2.deisiGreatGame;

public class SyntaxError extends Abyss{
    public SyntaxError(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String getTitleInfo(){
        return title;
    }

    @Override
    public String getImagePng(){
        return "syntax.png";
    }
}
