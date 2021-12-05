package pt.ulusofona.lp2.deisiGreatGame;

import java.util.HashMap;

public class Crash extends Abyss {
    public Crash(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public String getImagePng() {
        return "crash.png";
    }

}
