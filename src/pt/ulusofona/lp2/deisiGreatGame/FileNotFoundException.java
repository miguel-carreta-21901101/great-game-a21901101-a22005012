package pt.ulusofona.lp2.deisiGreatGame;

import java.util.HashMap;

public class FileNotFoundException extends Abyss {
    public FileNotFoundException(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }


    @Override
    public String getImagePng() {
        return "file-not-found-exception.png";
    }


}
