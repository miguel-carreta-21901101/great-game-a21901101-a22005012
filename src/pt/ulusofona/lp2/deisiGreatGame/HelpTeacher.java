package pt.ulusofona.lp2.deisiGreatGame;

public class HelpTeacher extends Tool{
    public HelpTeacher(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }

    public HelpTeacher(int id, String title){
        super(id, title);

    }

    @Override
    public String getImagePng(){
        return "ajuda-professor.png";
    }
}
