package pt.ulusofona.lp2.deisiGreatGame;

public class FileNotFoundException extends Abyss{
    public FileNotFoundException(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String toString(){
        return title;
    }

    @Override
    public String getImagePng(){
        return "file-not-found-exception.png";
    }
}
