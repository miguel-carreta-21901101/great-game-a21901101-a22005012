package pt.ulusofona.lp2.deisiGreatGame;

public class DuplicatedCode extends Abyss{
    public DuplicatedCode(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String getTitleInfo(){
        return title;
    }

    @Override
    public String getImagePng(){
        return "duplicated-code.png";
    }
}
