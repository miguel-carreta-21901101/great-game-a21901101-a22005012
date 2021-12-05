package pt.ulusofona.lp2.deisiGreatGame;

public class HelpTeacher extends Tool{
    public HelpTeacher(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }


    @Override
    public String getImagePng(){
        return "ajuda-professor.png";
    }
}
